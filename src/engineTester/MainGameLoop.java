package engineTester;

import GUI.GuiRenderer;
import GUI.GuiTexture;
import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {


    public static void main(String[] argv) {

        try {
            DisplayManager.createDisplay();

            Loader loader = new Loader();

            List<Entity> entities = new ArrayList<Entity>();

            RawModel rawModel = OBJLoader.loadObjModel("Sylvanas", loader);

            TexturedModel sylvanas = new TexturedModel(rawModel, new ModelTexture(loader.loadTexture("Sylvanas02")));
            ModelTexture texture = sylvanas.getTexture();
            texture.setShineDamper(10);
            texture.setReflectivity(1);

            // *********************************** Terrain Texture **********************************
            TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
            TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
            TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
            TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

            TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
            TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
            Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightMap");
            // *********************************** Terrain Texture **********************************

            //  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ GRASS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
                    new ModelTexture(loader.loadTexture("grassTexture")));
            grass.getTexture().setHasTransparency(true);
            grass.getTexture().setUseFakeLighting(true);

            //  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ FERN ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
            fernTextureAtlas.setNumberOfRows(2);
            TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
            fern.getTexture().setUseFakeLighting(true);
            fern.getTexture().setHasTransparency(true);

            // ~~~~~~~~~~~~~~~~~~~~ FLOWER ~~~~~~~~~~~~~~~~~~~~~~~~~~~
            TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
                    new ModelTexture(loader.loadTexture("flower")));
            flower.getTexture().setHasTransparency(true);
            flower.getTexture().setUseFakeLighting(true);


            TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("pine", loader),
                    new ModelTexture(loader.loadTexture("pine")));

            // ~~~~~~~~~~~~~~~~~~~~ LAMP ~~~~~~~~~~~~~~~~~~~~~~~~~~~
            TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader), new ModelTexture(loader.loadTexture("lamp")));
            lamp.getTexture().setHasTransparency(true);
            lamp.getTexture().setUseFakeLighting(true);

            Light lamp_light1 = new Light(new Vector3f(20, 14.7f, -10f), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f));
            Light lamp_light2 = new Light(new Vector3f(20, 14.7f, -50f), new Vector3f(0, 2, 0), new Vector3f(1, 0.01f, 0.002f));
            Light lamp_light3 = new Light(new Vector3f(50, 14.7f, -50), new Vector3f(2, 0, 2), new Vector3f(1, 0.01f, 0.002f));

            Entity lampEntity = new Entity(lamp, new Vector3f(20, 0, -10f), 0, 0, 0, 1);

            entities.add(lampEntity);
            entities.add(new Entity(lamp, new Vector3f(20, 0, -50f), 0, 0, 0, 1));
            entities.add(new Entity(lamp, new Vector3f(50, 0, -50), 0, 0, 0, 1));

            List<Light> lights = new ArrayList<Light>();
            lights.add(new Light(new Vector3f(2000, 1000, 3000), new Vector3f(0.4f, 0.4f, 0.4f)));
            lights.add(lamp_light1);
            lights.add(lamp_light2);
            lights.add(lamp_light3);


            Player bunny = new Player(sylvanas, new Vector3f(0, 0, -25), 0, 0, 0, 5);
            Camera camera = new Camera(bunny);

//            Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap, "heightMap");

            MasterRenderer renderer = new MasterRenderer(loader);

            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ W A T E R ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            WaterShader waterShader = new WaterShader();
            WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix());
            List<WaterTile> waterTiles = new ArrayList<WaterTile>();
            waterTiles.add(new WaterTile(75, -200, -1));

            WaterFrameBuffers fbos = new WaterFrameBuffers();

            Random random = new Random();
            for (int i = 0; i < 300; ++i) {
//                float x0 = random.nextFloat() * 800 - 400;
//                float z0 = random.nextFloat() * -600;
//                float y0 = terrain.getHeightOfTerrain(x0, z0);
//                entities.add(new Entity(flower, new Vector3f(x0, y0, z0), 0, 0, 1, 1.5f));

                float x1 = random.nextFloat() * 800 - 400;
                float z1 = random.nextFloat() * -600;
                float y1 = terrain.getHeightOfTerrain(x1, z1);
                entities.add(new Entity(tree, new Vector3f(x1, y1, z1), 0, 0, 1, 1f));

//                float x2 = random.nextFloat() * 800 - 400;
//                float z2 = random.nextFloat() * -600;
//                float y2 = terrain.getHeightOfTerrain(x2, z2);
//                entities.add(new Entity(grass, new Vector3f(x2, y2, z2), 0, 0, 1, 1f));

                float x3 = random.nextFloat() * 800 - 400;
                float z3 = random.nextFloat() * -600;
                float y3 = terrain.getHeightOfTerrain(x3, z3);
                entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 0.6f, random.nextInt(4)));
            }

            List<GuiTexture> guis = new ArrayList<GuiTexture>();
            GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.6f, 0.75f), new Vector2f(0.25f, 0.35f));
            GuiTexture waterGui = new GuiTexture(fbos.getReflectionTexture(), new Vector2f(0.5f, 0.5f), new Vector2f(0.3f, 0.3f));
            guis.add(gui);
            guis.add(waterGui);

            GuiRenderer guiRenderer = new GuiRenderer(loader);

            MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);

            while (!Display.isCloseRequested()) {

                camera.move();
                bunny.move(terrain);

                picker.update();

                Vector3f terrainPoint = picker.getCurrentTerrainPoint();


                fbos.bindReflectionFrameBuffer();
                if (terrainPoint != null) {
                    lampEntity.setPosition(terrainPoint);
                    lamp_light1.setPosition(new Vector3f(terrainPoint.x, terrainPoint.y + 15, terrainPoint.z));
                }

                renderer.processTerrain(terrain);
                renderer.processEntity(bunny);

                for (Entity entity : entities) {
                    renderer.processEntity(entity);
                }
                renderer.render(lights, camera);
                fbos.unbindCurrentFrameBuffer();


                if (terrainPoint != null) {
                    lampEntity.setPosition(terrainPoint);
                    lamp_light1.setPosition(new Vector3f(terrainPoint.x, terrainPoint.y + 15, terrainPoint.z));
                }

                renderer.processTerrain(terrain);
                renderer.processEntity(bunny);

                for (Entity entity : entities) {
                    renderer.processEntity(entity);
                }
                renderer.render(lights, camera);

                waterRenderer.render(waterTiles, camera);

                guiRenderer.render(guis);

                DisplayManager.updateDisplay();
            }

            fbos.cleanUp();
            guiRenderer.cleanUp();
            renderer.cleanUp();
            loader.cleanUp();
            DisplayManager.closeDisplay();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
