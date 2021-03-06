package com.ss.editor.tree.generator.parameters;

import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import com.jme3.util.clone.Cloner;
import com.simsilica.arboreal.Parameters;
import com.ss.editor.annotation.JmeThread;
import com.ss.editor.util.EditorUtil;
import com.ss.rlib.common.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * The class to present all materials as node in structure.
 *
 * @author JavaSaBr
 */
public class MaterialsParameters extends Parameters {

    /**
     * The tree material.
     */
    @NotNull
    private Material treeMaterial;

    /**
     * The flat material.
     */
    @NotNull
    private Material flatMaterial;

    /**
     * The impostor material.
     */
    @NotNull
    private Material impostorMaterial;

    /**
     * The leaf material.
     */
    @NotNull
    private Material leafMaterial;

    public MaterialsParameters() {
    }

    /**
     * Load default tree's materials.
     */
    @JmeThread
    public void loadDefault() {

        var assetManager = EditorUtil.getAssetManager();

        var bark = assetManager.loadTexture("Textures/bark128.jpg");
        bark.setWrap(Texture.WrapMode.Repeat);

        var barkNormals = assetManager.loadTexture("Textures/bark128-norm.jpg");
        barkNormals.setWrap(Texture.WrapMode.Repeat);

        var barkBumps = assetManager.loadTexture("Textures/bark128-bump.png");
        barkBumps.setWrap(Texture.WrapMode.Repeat);

        var leafAtlas = assetManager.loadTexture("Textures/leaf-atlas.png");
        leafAtlas.setWrap(Texture.WrapMode.Repeat);

        var noise = assetManager.loadTexture("Textures/noise-x3-512.png");
        noise.setWrap(Texture.WrapMode.Repeat);

        treeMaterial = new Material(assetManager, "MatDefs/TreeLighting.j3md");
        treeMaterial.setColor("Diffuse", ColorRGBA.White);
        treeMaterial.setColor("Ambient", ColorRGBA.White);
        treeMaterial.setBoolean("UseMaterialColors", true);
        treeMaterial.setBoolean("UseWind", false);
        treeMaterial.setTexture("WindNoise", noise);
        treeMaterial.setTexture("DiffuseMap", bark);
        treeMaterial.setTexture("NormalMap", barkNormals);
        treeMaterial.setTexture("ParallaxMap", barkBumps);

        flatMaterial = new Material(assetManager, "MatDefs/AxisBillboardLighting.j3md");
        flatMaterial.setColor("Diffuse", ColorRGBA.White);
        flatMaterial.setColor("Ambient", ColorRGBA.White);
        flatMaterial.setBoolean("UseMaterialColors", true);
        flatMaterial.setTexture("DiffuseMap", bark);
        flatMaterial.setBoolean("UseWind", false);
        flatMaterial.setTexture("WindNoise", noise);
        flatMaterial.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);

        impostorMaterial = new Material(assetManager, "MatDefs/IndexedBillboardLighting.j3md");
        impostorMaterial.setColor("Diffuse", ColorRGBA.White);
        impostorMaterial.setColor("Ambient", ColorRGBA.White);
        impostorMaterial.setBoolean("UseMaterialColors", true);
        impostorMaterial.setFloat("AlphaDiscardThreshold", 0.5f);
        impostorMaterial.setBoolean("UseWind", false);
        impostorMaterial.setTexture("WindNoise", noise);
        impostorMaterial.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        impostorMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        leafMaterial = new Material(assetManager, "MatDefs/LeafLighting.j3md");
        leafMaterial.setColor("Diffuse", ColorRGBA.White);
        leafMaterial.setColor("Ambient", ColorRGBA.White);
        leafMaterial.setBoolean("UseMaterialColors", true);
        leafMaterial.setTexture("DiffuseMap", leafAtlas);
        leafMaterial.setBoolean("UseWind", false);
        leafMaterial.setTexture("WindNoise", noise);
        leafMaterial.setFloat("AlphaDiscardThreshold", 0.5f);
        leafMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
    }

    /**
     * Load PBR tree's materials.
     */
    @JmeThread
    public void loadPBR() {

        var assetManager = EditorUtil.getAssetManager();

        var bark = assetManager.loadTexture("Textures/pbr/bark_diffuse.png");
        bark.setWrap(Texture.WrapMode.Repeat);

        var barkNormals = assetManager.loadTexture("Textures/pbr/bark_normal.png");
        barkNormals.setWrap(Texture.WrapMode.Repeat);

        var barkRoughness = assetManager.loadTexture("Textures/pbr/bark_rough.png");
        barkRoughness.setWrap(Texture.WrapMode.Repeat);

        var leafDiffuse = assetManager.loadTexture("Textures/pbr/crone_diffuse.png");
        leafDiffuse.setWrap(Texture.WrapMode.Repeat);

        var leafNormal = assetManager.loadTexture("Textures/pbr/crone_normal.png");
        leafNormal.setWrap(Texture.WrapMode.Repeat);

        var leafRoughness = assetManager.loadTexture("Textures/pbr/crone_rough.png");
        leafRoughness.setWrap(Texture.WrapMode.Repeat);

        var noise = assetManager.loadTexture("Textures/noise-x3-512.png");
        noise.setWrap(Texture.WrapMode.Repeat);

        treeMaterial = new Material(assetManager, "MatDefs/TreePBRLighting.j3md");
        treeMaterial.setFloat("Metallic", 0.1F);
        treeMaterial.setBoolean("UseWind", false);
        treeMaterial.setTexture("WindNoise", noise);
        treeMaterial.setTexture("BaseColorMap", bark);
        treeMaterial.setTexture("RoughnessMap", barkRoughness);
        treeMaterial.setTexture("NormalMap", barkNormals);

        flatMaterial = new Material(assetManager, "MatDefs/AxisBillboardPBRLighting.j3md");
        flatMaterial.setFloat("Metallic", 0.1F);
        flatMaterial.setTexture("BaseColorMap", bark);
        flatMaterial.setTexture("RoughnessMap", barkRoughness);
        flatMaterial.setBoolean("UseWind", false);
        flatMaterial.setTexture("WindNoise", noise);
        flatMaterial.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);

        impostorMaterial = new Material(assetManager, "MatDefs/IndexedBillboardLighting.j3md");
        impostorMaterial.setColor("Diffuse", ColorRGBA.White);
        impostorMaterial.setColor("Ambient", ColorRGBA.White);
        impostorMaterial.setBoolean("UseMaterialColors", true);
        impostorMaterial.setFloat("AlphaDiscardThreshold", 0.5f);
        impostorMaterial.setBoolean("UseWind", false);
        impostorMaterial.setTexture("WindNoise", noise);
        impostorMaterial.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        impostorMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        leafMaterial = new Material(assetManager, "MatDefs/LeafPBRLighting.j3md");
        leafMaterial.setFloat("Metallic", 0.1F);
        leafMaterial.setFloat("Roughness", 2F);
        leafMaterial.setTexture("BaseColorMap", leafDiffuse);
        leafMaterial.setTexture("NormalMap", leafNormal);
        leafMaterial.setTexture("RoughnessMap", leafRoughness);
        leafMaterial.setBoolean("UseWind", false);
        leafMaterial.setTexture("WindNoise", noise);
        leafMaterial.setFloat("AlphaDiscardThreshold", 0.7f);
        leafMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
    }

    /**
     * Get the tree material.
     *
     * @return the tree material.
     */
    @JmeThread
    public @NotNull Material getTreeMaterial() {
        return treeMaterial;
    }

    /**
     * Get the tree material.
     *
     * @param treeMaterial the tree material.
     */
    @JmeThread
    public void setTreeMaterial(@NotNull Material treeMaterial) {
        this.treeMaterial = treeMaterial;
    }

    /**
     * Get the flat material.
     *
     * @return the flat material.
     */
    @JmeThread
    public @NotNull Material getFlatMaterial() {
        return flatMaterial;
    }

    /**
     * Get the flat material.
     *
     * @param flatMaterial the flat material.
     */
    @JmeThread
    public void setFlatMaterial(@NotNull Material flatMaterial) {
        this.flatMaterial = flatMaterial;
    }

    /**
     * Get the impostor material.
     *
     * @return the impostor material.
     */
    @JmeThread
    public @NotNull Material getImpostorMaterial() {
        return impostorMaterial;
    }

    /**
     * Get the impostor material.
     *
     * @param impostorMaterial the impostor material.
     */
    @JmeThread
    public void setImpostorMaterial(@NotNull Material impostorMaterial) {
        this.impostorMaterial = impostorMaterial;
    }

    /**
     * Get the leaf material.
     *
     * @return the leaf material.
     */
    @JmeThread
    public @NotNull Material getLeafMaterial() {
        return leafMaterial;
    }

    /**
     * Get the leaf material.
     *
     * @param leafMaterial the leaf material.
     */
    @JmeThread
    public void setLeafMaterial(@NotNull Material leafMaterial) {
        this.leafMaterial = leafMaterial;
    }

    @Override
    @JmeThread
    public void cloneFields(@NotNull Cloner cloner, @NotNull Object original) {
        super.cloneFields(cloner, original);
        treeMaterial = cloner.clone(treeMaterial);
        flatMaterial = cloner.clone(flatMaterial);
        impostorMaterial = cloner.clone(impostorMaterial);
        leafMaterial = cloner.clone(leafMaterial);
    }

    @Override
    @JmeThread
    public void write(@NotNull JmeExporter ex) throws IOException {
        super.write(ex);

        var out = ex.getCapsule(this);
        out.write(treeMaterial, "treeMaterial", null);
        out.write(flatMaterial, "flatMaterial", null);
        out.write(impostorMaterial, "impostorMaterial", null);
        out.write(leafMaterial, "leafMaterial", null);
        out.write(treeMaterial.getAssetName(), "treeMaterialName", null);
        out.write(flatMaterial.getAssetName(), "flatMaterialName", null);
        out.write(impostorMaterial.getAssetName(), "impostorMaterialName", null);
        out.write(leafMaterial.getAssetName(), "leafMaterialName", null);
    }

    @Override
    @JmeThread
    public void read(@NotNull final JmeImporter im) throws IOException {
        super.read(im);

        var assetManager = im.getAssetManager();
        var in = im.getCapsule(this);

        treeMaterial = (Material) in.readSavable("treeMaterial", null);
        flatMaterial = (Material) in.readSavable("flatMaterial", null);
        impostorMaterial = (Material) in.readSavable("impostorMaterial", null);
        leafMaterial = (Material) in.readSavable("leafMaterial", null);

        var matName = in.readString("treeMaterialName", null);
        if (matName != null) {
            treeMaterial = Utils.safeGetOpt(assetManager, matName, AssetManager::loadMaterial)
                    .orElse(treeMaterial);
        }
        matName = in.readString("flatMaterialName", null);
        if (matName != null) {
            flatMaterial = Utils.safeGetOpt(assetManager, matName, AssetManager::loadMaterial)
                    .orElse(flatMaterial);
        }
        matName = in.readString("impostorMaterialName", null);
        if (matName != null) {
            impostorMaterial = Utils.safeGetOpt(assetManager, matName, AssetManager::loadMaterial)
                    .orElse(impostorMaterial);
        }
        matName = in.readString("leafMaterialName", null);
        if (matName != null) {
            leafMaterial = Utils.safeGetOpt(assetManager, matName, AssetManager::loadMaterial)
                    .orElse(leafMaterial);
        }
    }
}
