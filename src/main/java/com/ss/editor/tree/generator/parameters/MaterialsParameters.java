package com.ss.editor.tree.generator.parameters;

import com.jme3.asset.AssetManager;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.util.clone.Cloner;
import com.simsilica.arboreal.Parameters;
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

    public MaterialsParameters(@NotNull final AssetManager assetManager) {

        final Texture bark = assetManager.loadTexture("Textures/bark128.jpg");
        bark.setWrap(WrapMode.Repeat);

        final Texture barkNormals = assetManager.loadTexture("Textures/bark128-norm.jpg");
        barkNormals.setWrap(WrapMode.Repeat);

        final Texture barkBumps = assetManager.loadTexture("Textures/bark128-bump.png");
        barkBumps.setWrap(WrapMode.Repeat);

        final Texture leafAtlas = assetManager.loadTexture("Textures/leaf-atlas.png");
        leafAtlas.setWrap(WrapMode.Repeat);

        final Texture noise = assetManager.loadTexture("Textures/noise-x3-512.png");
        noise.setWrap(WrapMode.Repeat);

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
     * @return the tree material.
     */
    public @NotNull Material getTreeMaterial() {
        return treeMaterial;
    }

    /**
     * @param treeMaterial the tree material.
     */
    public void setTreeMaterial(@NotNull final Material treeMaterial) {
        this.treeMaterial = treeMaterial;
    }

    /**
     * @return the flat material.
     */
    public @NotNull Material getFlatMaterial() {
        return flatMaterial;
    }

    /**
     * @param flatMaterial the flat material.
     */
    public void setFlatMaterial(@NotNull final Material flatMaterial) {
        this.flatMaterial = flatMaterial;
    }

    /**
     * @return the impostor material.
     */
    public @NotNull Material getImpostorMaterial() {
        return impostorMaterial;
    }

    /**
     * @param impostorMaterial the impostor material.
     */
    public void setImpostorMaterial(@NotNull final Material impostorMaterial) {
        this.impostorMaterial = impostorMaterial;
    }

    /**
     * @return the leaf material.
     */
    public @NotNull Material getLeafMaterial() {
        return leafMaterial;
    }

    /**
     * @param leafMaterial the leaf material.
     */
    public void setLeafMaterial(@NotNull final Material leafMaterial) {
        this.leafMaterial = leafMaterial;
    }

    @Override
    public void cloneFields(@NotNull final Cloner cloner, @NotNull final Object original) {
        super.cloneFields(cloner, original);
        treeMaterial = cloner.clone(treeMaterial);
        flatMaterial = cloner.clone(flatMaterial);
        impostorMaterial = cloner.clone(impostorMaterial);
        leafMaterial = cloner.clone(leafMaterial);
    }

    @Override
    public void write(@NotNull final JmeExporter ex) throws IOException {
        super.write(ex);
        final OutputCapsule out = ex.getCapsule(this);
        out.write(treeMaterial, "treeMaterial", null);
        out.write(flatMaterial, "flatMaterial", null);
        out.write(impostorMaterial, "impostorMaterial", null);
        out.write(leafMaterial, "leafMaterial", null);
    }

    @Override
    public void read(@NotNull final JmeImporter im) throws IOException {
        super.read(im);
        final InputCapsule in = im.getCapsule(this);
        treeMaterial = (Material) in.readSavable("treeMaterial", null);
        flatMaterial = (Material) in.readSavable("flatMaterial", null);
        impostorMaterial = (Material) in.readSavable("impostorMaterial", null);
        leafMaterial = (Material) in.readSavable("leafMaterial", null);
    }
}
