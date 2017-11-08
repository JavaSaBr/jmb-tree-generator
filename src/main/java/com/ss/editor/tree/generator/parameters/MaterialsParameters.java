package com.ss.editor.tree.generator.parameters;

import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import com.jme3.util.clone.Cloner;
import com.simsilica.arboreal.Parameters;
import com.ss.editor.Editor;
import com.ss.editor.annotation.JMEThread;
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

    @JMEThread
    public void loadDefault() {

        final Editor editor = Editor.getInstance();
        final AssetManager assetManager = editor.getAssetManager();

        final Texture bark = assetManager.loadTexture("Textures/bark128.jpg");
        bark.setWrap(Texture.WrapMode.Repeat);

        final Texture barkNormals = assetManager.loadTexture("Textures/bark128-norm.jpg");
        barkNormals.setWrap(Texture.WrapMode.Repeat);

        final Texture barkBumps = assetManager.loadTexture("Textures/bark128-bump.png");
        barkBumps.setWrap(Texture.WrapMode.Repeat);

        final Texture leafAtlas = assetManager.loadTexture("Textures/leaf-atlas.png");
        leafAtlas.setWrap(Texture.WrapMode.Repeat);

        final Texture noise = assetManager.loadTexture("Textures/noise-x3-512.png");
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

    @JMEThread
    public void loadPBR() {

        final Editor editor = Editor.getInstance();
        final AssetManager assetManager = editor.getAssetManager();

        final Texture bark = assetManager.loadTexture("Textures/pbr/bark_diffuse.png");
        bark.setWrap(Texture.WrapMode.Repeat);

        final Texture barkNormals = assetManager.loadTexture("Textures/pbr/bark_normal.png");
        barkNormals.setWrap(Texture.WrapMode.Repeat);

        final Texture barkRoughness = assetManager.loadTexture("Textures/pbr/bark_rough.png");
        barkRoughness.setWrap(Texture.WrapMode.Repeat);

        final Texture leafDiffuse = assetManager.loadTexture("Textures/pbr/crone_diffuse.png");
        leafDiffuse.setWrap(Texture.WrapMode.Repeat);

        final Texture leafNormal = assetManager.loadTexture("Textures/pbr/crone_normal.png");
        leafNormal.setWrap(Texture.WrapMode.Repeat);

        final Texture leafRoughness = assetManager.loadTexture("Textures/pbr/crone_rough.png");
        leafRoughness.setWrap(Texture.WrapMode.Repeat);

        final Texture noise = assetManager.loadTexture("Textures/noise-x3-512.png");
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
     * @return the tree material.
     */
    @JMEThread
    public @NotNull Material getTreeMaterial() {
        return treeMaterial;
    }

    /**
     * @param treeMaterial the tree material.
     */
    @JMEThread
    public void setTreeMaterial(@NotNull final Material treeMaterial) {
        this.treeMaterial = treeMaterial;
    }

    /**
     * @return the flat material.
     */
    @JMEThread
    public @NotNull Material getFlatMaterial() {
        return flatMaterial;
    }

    /**
     * @param flatMaterial the flat material.
     */
    @JMEThread
    public void setFlatMaterial(@NotNull final Material flatMaterial) {
        this.flatMaterial = flatMaterial;
    }

    /**
     * @return the impostor material.
     */
    @JMEThread
    public @NotNull Material getImpostorMaterial() {
        return impostorMaterial;
    }

    /**
     * @param impostorMaterial the impostor material.
     */
    @JMEThread
    public void setImpostorMaterial(@NotNull final Material impostorMaterial) {
        this.impostorMaterial = impostorMaterial;
    }

    /**
     * @return the leaf material.
     */
    @JMEThread
    public @NotNull Material getLeafMaterial() {
        return leafMaterial;
    }

    /**
     * @param leafMaterial the leaf material.
     */
    @JMEThread
    public void setLeafMaterial(@NotNull final Material leafMaterial) {
        this.leafMaterial = leafMaterial;
    }

    @Override
    @JMEThread
    public void cloneFields(@NotNull final Cloner cloner, @NotNull final Object original) {
        super.cloneFields(cloner, original);
        treeMaterial = cloner.clone(treeMaterial);
        flatMaterial = cloner.clone(flatMaterial);
        impostorMaterial = cloner.clone(impostorMaterial);
        leafMaterial = cloner.clone(leafMaterial);
    }

    @Override
    @JMEThread
    public void write(@NotNull final JmeExporter ex) throws IOException {
        super.write(ex);

        final OutputCapsule out = ex.getCapsule(this);
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
    @JMEThread
    public void read(@NotNull final JmeImporter im) throws IOException {
        super.read(im);

        final AssetManager assetManager = im.getAssetManager();
        final InputCapsule in = im.getCapsule(this);

        treeMaterial = (Material) in.readSavable("treeMaterial", null);
        flatMaterial = (Material) in.readSavable("flatMaterial", null);
        impostorMaterial = (Material) in.readSavable("impostorMaterial", null);
        leafMaterial = (Material) in.readSavable("leafMaterial", null);

        String matName = in.readString("treeMaterialName", null);
        if (matName != null) {
            try {
                treeMaterial = assetManager.loadMaterial(matName);
            } catch (final AssetNotFoundException ex) {
            }
        }
        matName = in.readString("flatMaterialName", null);
        if (matName != null) {
            try {
                flatMaterial = assetManager.loadMaterial(matName);
            } catch (final AssetNotFoundException ex) {
            }
        }
        matName = in.readString("impostorMaterialName", null);
        if (matName != null) {
            try {
                impostorMaterial = assetManager.loadMaterial(matName);
            } catch (final AssetNotFoundException ex) {
            }
        }
        matName = in.readString("leafMaterialName", null);
        if (matName != null) {
            try {
                leafMaterial = assetManager.loadMaterial(matName);
            } catch (final AssetNotFoundException ex) {
            }
        }
    }
}
