package renderEngine;

import entity.Entity;
import models.TexturedModel;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import models.RawModel;
import org.lwjgl.util.vector.Matrix4f;
import shader.StaticShader;
import textures.ModelTexture;
import toolbox.MyMath;

import java.util.List;
import java.util.Map;


public class EntityRenderer {

    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {

        this.shader = shader;

        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void render(Map<TexturedModel, List<Entity>> entities) {
        for (TexturedModel model : entities.keySet()) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel model) {
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();


        if (texture.hasTransparency()) {
            MasterRenderer.disableCulling();
        }

        shader.loadNumberOfRows(texture.getNumberOfRows());
        shader.loadFakeLightingVariable(texture.isUseFakeLighting());
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
    }

    private void unbindTexturedModel() {
        MasterRenderer.enableCulling();
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        Matrix4f transformationMatrix = MyMath.createTransformationMatrix(entity.getPosition(),
                entity.getRx(), entity.getRy(), entity.getRz(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        shader.loadOffset(entity.getTextureOffsetX(), entity.getTextureOffsetY());
    }

//    public void render(Entity entity, StaticShader shader) {
//
//        TexturedModel model = entity.getModel();
//        RawModel rawModel = model.getRawModel();
//
//        GL30.glBindVertexArray(rawModel.getVaoID());
//
//        GL20.glEnableVertexAttribArray(0);
//        GL20.glEnableVertexAttribArray(1);
//        GL20.glEnableVertexAttribArray(2);
//
//        Matrix4f transformationMatrix = MyMath.createTransformationMatrix(entity.getPosition(),
//                entity.getRx(), entity.getRy(), entity.getRz(), entity.getScale());
//        shader.loadTransformationMatrix(transformationMatrix);
//        ModelTexture texture = model.getTexture();
//        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
//
//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
//
//        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//
//        GL20.glDisableVertexAttribArray(0);
//        GL20.glDisableVertexAttribArray(1);
//        GL20.glDisableVertexAttribArray(2);
//
//        GL30.glBindVertexArray(0);
//    }

//    public void render(TexturedModel model) {
//
//        GL30.glBindVertexArray(model.getRawModel().getVaoID());
//
//        GL20.glEnableVertexAttribArray(0);
//        GL20.glEnableVertexAttribArray(1);
//
//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
//
//        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//
//        GL20.glDisableVertexAttribArray(0);
//        GL20.glDisableVertexAttribArray(1);
//
//        GL30.glBindVertexArray(0);
//    }

//    public void render(RawModel model) {
//
//        GL30.glBindVertexArray(model.getVaoID());
//        GL20.glEnableVertexAttribArray(0);
//        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
//        GL20.glDisableVertexAttribArray(0);
//        GL30.glBindVertexArray(0);
//    }


}
