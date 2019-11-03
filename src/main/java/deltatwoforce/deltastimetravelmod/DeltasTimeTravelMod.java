package deltatwoforce.deltastimetravelmod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.server.management.PlayerInteractionManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import deltatwoforce.deltastimetravelmod.blocks.TileEntityCar;
import deltatwoforce.deltastimetravelmod.blocks.TileEntityCarSpecialRenderer;
import deltatwoforce.deltastimetravelmod.blocks.TimeTravelBlocks;
import deltatwoforce.deltastimetravelmod.entity.CarEntity;
import deltatwoforce.deltastimetravelmod.entity.CarEntityRendererFactory;
import deltatwoforce.deltastimetravelmod.entity.PastPlayerEntity;
import deltatwoforce.deltastimetravelmod.entity.PastPlayerEntityRenderer;
import deltatwoforce.deltastimetravelmod.entity.PastPlayerEntityRendererFactory;
import deltatwoforce.deltastimetravelmod.items.TimeTravelItems;
import deltatwoforce.deltastimetravelmod.snapshot.ISnapshot;
import deltatwoforce.deltastimetravelmod.snapshot.SnapshotChangeBlock;
import deltatwoforce.deltastimetravelmod.snapshot.SnapshotEntityDespawn;
import deltatwoforce.deltastimetravelmod.snapshot.SnapshotEntityMove;
import deltatwoforce.deltastimetravelmod.snapshot.SnapshotEntitySpawn;
import deltatwoforce.deltastimetravelmod.snapshot.SnapshotTime;
import deltatwoforce.deltastimetravelmod.utils.TickSnapshot;

@Mod(modid = DeltasTimeTravelMod.MODID, name = DeltasTimeTravelMod.NAME, version = DeltasTimeTravelMod.VERSION)
@Mod.EventBusSubscriber(modid=DeltasTimeTravelMod.MODID)
public class DeltasTimeTravelMod
{
    public static final String MODID = "deltastimetravelmod";
    public static final String NAME = "Delta's Time Travel Mod";
    public static final String VERSION = "1.0";
    public static ArrayList<TickSnapshot> timeline;
    public static EntityPlayer player;
    public static PastPlayerEntity pastPlayer;
    public static boolean recordTime;
    public static boolean inThePast;
    public static int currentTick;
    public static Gson gson = new Gson();
    public static TickSnapshot currentTickSnapshot;
    public static boolean firstFrameDone;

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	timeline = new ArrayList<>();
        logger = event.getModLog();
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "FakePastPlayer"), PastPlayerEntity.class, "FakePastPlayer", 5, this, 4096, 1, false);
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "Car"), CarEntity.class, "Car", 6, this, 80, 1, false);
        GameRegistry.registerTileEntity(TileEntityCar.class, new ResourceLocation(MODID, "TileEntityCar"));
        registerModRenderer();
    }
    
    @SideOnly(Side.CLIENT)
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCar.class, new TileEntityCarSpecialRenderer());
    }
    
    @SideOnly(Side.CLIENT)
    public void registerModRenderer() {
    	RenderingRegistry.registerEntityRenderingHandler(PastPlayerEntity.class, PastPlayerEntityRendererFactory.INSTANCE);
    	RenderingRegistry.registerEntityRenderingHandler(CarEntity.class, CarEntityRendererFactory.INSTANCE);
    }
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
    	event.getRegistry().register(TimeTravelBlocks.CAR);
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    	event.getRegistry().register(TimeTravelItems.TIME_TRAVEL_STICK);
    	event.getRegistry().register(TimeTravelItems.CAR);
    }
    
    @SubscribeEvent
    public static void onTick(TickEvent.ServerTickEvent event) {
    	if(recordTime) {
        	if(timeline.size() == 12000) {
        		timeline.remove(0);
        	}
        	if(currentTickSnapshot != null) {
        		timeline.add(currentTickSnapshot);
        		currentTickSnapshot=null;
        	}
    		currentTickSnapshot = new TickSnapshot();
    		currentTickSnapshot.extra = new ArrayList<>();
    		currentTickSnapshot.position = player.getPositionVector();
    		currentTickSnapshot.itemInHand = player.getHeldItemMainhand();
    		currentTickSnapshot.yaw = player.rotationYawHead;
    		currentTickSnapshot.pitch = player.rotationPitch;
    		currentTickSnapshot.extra.add(new SnapshotTime(player.world));
    	}
    	if(inThePast) {
    		if(pastPlayer == null) {
    			pastPlayer = new PastPlayerEntity(player.world, player.getGameProfile());
    			Vec3d firstPos = timeline.get(0).position;
    			pastPlayer.setPosition(firstPos.x, firstPos.y, firstPos.z);
    			pastPlayer.forceSpawn = true;
    			player.world.spawnEntity(pastPlayer);
    		}else {
    			if(pastPlayer.isEntityAlive()) {
            		TickSnapshot ts = timeline.get(currentTick);
            		pastPlayer.setPositionAndRotation(ts.position.x, ts.position.y, ts.position.z, ts.yaw, ts.pitch);
            		pastPlayer.setRotationYawHead(ts.yaw);
            		pastPlayer.setHeldItem(EnumHand.MAIN_HAND, ts.itemInHand);
            		pastPlayer.setHealth(100f);
            		for(ISnapshot sn : ts.extra) {
            			sn.doTick();
            		}
            		currentTick++;
    			}
    		}
    		if(currentTick == timeline.size()) {
    			pastPlayer.setHealth(-1);
    			pastPlayer.world.removeEntity(pastPlayer);
    			pastPlayer = null;
    			inThePast = false;
    			timeline.clear();
    			recordTime = true;
    			currentTick = 0;
    		}
    	}
    }
    
    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
    	if(event.getEntity() instanceof PastPlayerEntity) {
    		event.setCanceled(true);
    	}
    }
    
    @SubscribeEvent
    public static void onBlockBroken(BlockEvent.BreakEvent event) {
    	if(currentTickSnapshot != null && recordTime) {
    		currentTickSnapshot.extra.add(new SnapshotChangeBlock(event.getPos(), event.getState(), Blocks.AIR.getDefaultState(), event.getWorld()));
    	}
    }
    
    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.PlaceEvent event) {
    	if(currentTickSnapshot != null && recordTime) {
    		currentTickSnapshot.extra.add(new SnapshotChangeBlock(event.getPos(), Blocks.AIR.getDefaultState(), event.getPlacedBlock(), event.getWorld()));
    	}
    }
    
    @SubscribeEvent
    public static void onSpawn(PlayerEvent.PlayerLoggedInEvent event) {
    	recordTime = true;
    	player = event.player;
    	currentTick = 0;
    	
    	currentTickSnapshot = new TickSnapshot();
    	currentTickSnapshot.extra = new ArrayList<>();
    	currentTickSnapshot.position = player.getPositionVector();
		currentTickSnapshot.itemInHand = player.getHeldItemMainhand();
		currentTickSnapshot.yaw = player.rotationYawHead;
		currentTickSnapshot.pitch = player.rotationPitch;
		currentTickSnapshot.extra.add(new SnapshotTime(player.world));
		timeline.add(currentTickSnapshot);
    }
    
    @SubscribeEvent
    public static void onDespawn(PlayerEvent.PlayerLoggedOutEvent event) {
    	timeline.clear();
    	if(pastPlayer != null) {
        	pastPlayer.world.removeEntity(pastPlayer);
    	}
    	recordTime = false;
    	inThePast = false;
    	currentTick = 0;
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
    	ModelLoader.setCustomModelResourceLocation(TimeTravelItems.TIME_TRAVEL_STICK, 0, new ModelResourceLocation(MODID + ":time_travel_stick"));
    	ModelLoader.setCustomModelResourceLocation(TimeTravelItems.CAR, 0, new ModelResourceLocation(MODID + ":car"));
    }
}
