package com.example.sdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.util.HashMap;
import java.util.Map;

public class PanouriActivity extends AppCompatActivity implements View.OnClickListener {

    private ArFragment p_fragment;
    private ImageView p_pan,p_pan2;
    private ModelRenderable p_pan_3D,p_pan_3D2;


    private final Map<AugmentedImage, AugmentedImageNode> augmentedImageMap = new HashMap<>();
    View arrayView[];


    int selected=1;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panouri);
        p_fragment=(ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment_1);
        HitResult hitResult;
        p_fragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
        p_pan=findViewById(R.id.p1);
        p_pan2=findViewById(R.id.p2);
        setArrayView();
        setClickListener();
        setUpModel();
        p_fragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                if((plane.getType() == Plane.Type.VERTICAL) | (plane.getType() == Plane.Type.HORIZONTAL_UPWARD_FACING)){
                    Anchor a1= hitResult.createAnchor();
                    AnchorNode a1_node=new AnchorNode(a1);
                    a1_node.setParent(p_fragment.getArSceneView().getScene());
                    createModel(a1_node,selected);

                }
            }
        });
    }

    private void createModel(AnchorNode a1_node, int selected) {
        if(selected ==1){
            TransformableNode panou_1=new TransformableNode(p_fragment.getTransformationSystem());

            panou_1.setLocalScale(new Vector3(0.01f,0.01f,0.01f));
            panou_1.setParent(a1_node);

            panou_1.setRenderable(p_pan_3D);
            if (PanouriFragment.pColorChoice.getSelectedItem() == "Alb") {
                p_pan_3D.getMaterial().setFloat4("baseColor",new Color(1.0f,1.0f,1.0f,1.0f));

            }
            if (PanouriFragment.pColorChoice.getSelectedItem() == "Verde") {
                p_pan_3D.getMaterial().setFloat4("baseColor",new Color(0.0f,1.0f,0.0f,1.0f));

            }
            if (PanouriFragment.pColorChoice.getSelectedItem() == "Albastru") {
                p_pan_3D.getMaterial().setFloat4("baseColor",new Color(0.0f,0.0f,1.0f,1.0f));

            }
            panou_1.select();
        }
        if(selected ==2){
            TransformableNode panou_2=new TransformableNode(p_fragment.getTransformationSystem());

            panou_2.setLocalScale(new Vector3(0.01f,0.01f,0.01f));
            panou_2.setParent(a1_node);

            panou_2.setRenderable(p_pan_3D2);
            if (PanouriFragment.pColorChoice.getSelectedItem() == "Alb") {
                p_pan_3D2.getMaterial().setFloat4("baseColor",new Color(1.0f,1.0f,1.0f,1.0f));

            }
            if (PanouriFragment.pColorChoice.getSelectedItem() == "Verde") {
                p_pan_3D2.getMaterial().setFloat4("baseColor",new Color(0.0f,1.0f,0.0f,1.0f));

            }
            if (PanouriFragment.pColorChoice.getSelectedItem() == "Albastru") {
                p_pan_3D2.getMaterial().setFloat4("baseColor",new Color(0.0f,0.0f,1.0f,1.0f));

            }
            panou_2.select();
        }
    }

    private void setUpModel() {
        ModelRenderable.builder().setSource(this,R.raw.model_1)
                .build().thenAccept(renderable->p_pan_3D=renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this," Nu se poate incarca modelul",Toast.LENGTH_SHORT).show();
                    return null;
                });
        ModelRenderable.builder().setSource(this,R.raw.floare)
                .build().thenAccept(renderable->p_pan_3D2=renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this," Nu se poate incarca modelul",Toast.LENGTH_SHORT).show();
                    return null;
                });
    }
    private void setArrayView() {

        arrayView=new View[]{p_pan,p_pan2};

    }
    private void setClickListener() {
        for(int i=0;i<arrayView.length;i++){
            arrayView[i].setOnClickListener(this);
        }
    }



    private void onUpdateFrame(FrameTime frameTime) {
    }
    public void onClick(View v) {
        if(v.getId()==R.id.s1){
            selected=1;
        }else{
            selected=2;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
