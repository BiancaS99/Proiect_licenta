package com.example.sdesign;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.widget.Button;
import android.widget.ImageView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Camera;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.HitResult;
import com.google.ar.core.ImageFormat;
import com.google.ar.core.Plane.Type;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.core.Trackable;
import com.google.ar.core.TrackingState;
import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.core.exceptions.NotYetAvailableException;
import com.google.ar.core.exceptions.UnavailableApkTooOldException;
import com.google.ar.core.exceptions.UnavailableArcoreNotInstalledException;
import com.google.ar.core.exceptions.UnavailableDeviceNotCompatibleException;
import com.google.ar.core.exceptions.UnavailableSdkTooOldException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.Node;
 import  com.google.ar.sceneform.NodeParent;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

import static com.google.ar.core.TrackingState.PAUSED;
import static java.awt.font.TextAttribute.TRACKING;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ArFragment arFragment;
    private ImageView fitToScanView,cal,imag,scafa,scafa2,scafal;
    private ModelRenderable calRendabil,scafaRendabila1,scafaRendabila2,scafaRendabila_l;
     Session mSession;
    private Config mConfig;
    private boolean isTracking;
    private boolean isHitting;
    private Button save;
    Uri uri_attach;
    private static final int PICK_FROM_DOCUMENTS=101;
    int columnindex;
    String attachmentFile;
   Anchor startAnchor ;
   Pose pose;
    private final float[] viewMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final Map<AugmentedImage, AugmentedImageNode> augmentedImageMap = new HashMap<>();
    View arrayView[];


    int selected=1;

    public static Config.PlaneFindingMode HORIZONTAL_AND_VERTICAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);

        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);

        save=findViewById(R.id.m_save);
        imag=findViewById(R.id.img);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onSceneUpdate(arFragment.getArSceneView().getArFrame());
                takeScreenshot();
                sendEmail();
            }


        });

        scafa = findViewById(R.id.s1);
        scafa2=findViewById(R.id.s2);
        scafal=findViewById(R.id.s3);


        setArrayView();
        setClickListener();
        setUpModel();
//        try {
//          //  mSession.setCameraTextureName(backgroundRenderer.getTextureId());
//
//            Frame frame = mSession.update();
//            Camera camera = frame.getCamera();
//
//            // Compute lighting from average intensity of the image.
//            // 3 components here are color factors, the fourth one is intensity.
//
//            final float[] colorCorrectionRgba = new float[4];
//            frame.getLightEstimate().getColorCorrection(colorCorrectionRgba, 0);
//
//        } catch (Throwable t) {
//            //Log.e(TAG, "Exception on the OpenGL thread", t);
//        }




       arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
          @Override
           public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
              if ((plane.getType() == Plane.Type.VERTICAL) | (plane.getType() == Plane.Type.HORIZONTAL_UPWARD_FACING)) {

                    Anchor anchor = hitResult.createAnchor();
                    AnchorNode anchorNode = new AnchorNode(anchor);
                    anchorNode.setParent(arFragment.getArSceneView().getScene());
                    createModel(anchorNode, selected);
               }


           }
       });

    }
    protected void onActivityResult(int requestCode, int resultCode,Intent data_atta){
        if (requestCode==PICK_FROM_DOCUMENTS && resultCode== RESULT_OK){
            uri_attach=data_atta.getData();
            String[] filePath={MediaStore.Images.Media.DATA};
            Cursor cursor= getContentResolver().query(uri_attach,filePath,null,null,null);
            cursor.moveToFirst();
            columnindex=cursor.getColumnIndex(filePath[0]);
            attachmentFile=cursor.getString(columnindex);
            uri_attach=Uri.parse("/Schita.jpeg");
            cursor.close();
        }
    }
    public void sendEmail(){
        try{
            String email="solomonbianca72@gmail.com";
            String subiect="Deviz Incercari";
            String mesaj =" Termina o data cu disertatia ca sa bem sticla aia de cognac";
            final Intent emailIntent= new Intent((Intent.ACTION_SEND));
            emailIntent.setType("plain/text");
            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{subiect});
            if(uri_attach!=null){
                emailIntent.putExtra(Intent.EXTRA_STREAM,uri_attach);
            }
            // emailIntent.putExtra(Intent.EXTRA_TEXT,new String[]{mesaj});
            this.startActivity(Intent.createChooser(emailIntent,"Se trimite email-ul către client"));


        }catch(Throwable t){
            Toast.makeText(this,"Email-ul nu a putut fi trimis"+t.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private void takeScreenshot() {
       // view.setDrawingCacheEnabled(true);
        ArSceneView view = arFragment.getArSceneView();
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        final HandlerThread handlerThread = new HandlerThread("PixelCopier");
        handlerThread.start();
        // Make the request to copy.
        PixelCopy.request(view, bitmap, (copyResult) -> {
            if (copyResult == PixelCopy.SUCCESS) {
                try {
                    saveBitmapToDisk(bitmap);
                } catch (IOException e) {
                    Toast toast = Toast.makeText(MainActivity.this, e.toString(),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }





            }
            handlerThread.quitSafely();
        }, new Handler(handlerThread.getLooper()));
    }


    public void saveBitmapToDisk(Bitmap bitmap) throws IOException {

        //  String path = Environment.getExternalStorageDirectory().toString() +  "/Pictures/Screenshots/";




        File mediaFile = new File(Environment.getExternalStorageDirectory().toString(), "Schita"+".jpeg");

        FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    private void setUpModel() {
        ModelRenderable.builder().setSource(this,R.raw.scafa)
                .build().thenAccept(renderable->scafaRendabila1=renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this," Nu se poate incarca modelul",Toast.LENGTH_SHORT).show();
                    return null;
                });

        ModelRenderable.builder().setSource(this,R.raw.scafa2)
                .build().thenAccept(renderable->scafaRendabila2=renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this,"Nu se poate incarca modelul",Toast.LENGTH_SHORT).show();
                    return null;
                });
        ModelRenderable.builder().setSource(this,R.raw.scafal)
                .build().thenAccept(renderable->scafaRendabila_l=renderable)
                .exceptionally(throwable -> {
                    Toast.makeText(this," Nu se poate incarca modelul",Toast.LENGTH_SHORT).show();
                    return null;
                });

    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if(selected ==1){
            TransformableNode scafa=new TransformableNode(arFragment.getTransformationSystem());
            scafa.setParent(anchorNode);
           // cal.setLocalPosition();
            scafa.setRenderable(scafaRendabila1);

            if (ScafeFragment.sColorChoice.getSelectedItem() == "Alb") {
                scafaRendabila1.getMaterial().setFloat4("baseColor",new Color(1.0f,1.0f,1.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Verde") {
                scafaRendabila1.getMaterial().setFloat4("baseColor",new Color(0.0f,1.0f,0.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Mov") {
                scafaRendabila1.getMaterial().setFloat4("baseColor",new Color(1.0f,0.0f,1.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Albastru") {
                scafaRendabila1.getMaterial().setFloat4("baseColor",new Color(0.0f,0.0f,1.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Portocaliu") {
                scafaRendabila1.getMaterial().setFloat4("baseColor",new Color(1.0f,1.0f,0.0f,1.0f));

            }
         //   scafaRendabila1.getMaterial().setFloat4("baseColor",new Color(1.0f,0.0f,0.0f,1.0f));
          //  scafaRendabila1.set
            scafa.select();
        }
        if(selected==2){
            TransformableNode scafa2=new TransformableNode(arFragment.getTransformationSystem());
            scafa2.setParent(anchorNode);
            // cal.setLocalPosition();
            scafa2.setRenderable(scafaRendabila2);
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Alb") {
                scafaRendabila2.getMaterial().setFloat4("baseColor",new Color(1.0f,1.0f,1.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Verde") {
                scafaRendabila2.getMaterial().setFloat4("baseColor",new Color(0.0f,1.0f,0.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Mov") {
                scafaRendabila2.getMaterial().setFloat4("baseColor",new Color(1.0f,0.0f,1.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Albastru") {
                scafaRendabila2.getMaterial().setFloat4("baseColor",new Color(0.0f,0.0f,1.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Portocaliu") {
                scafaRendabila2.getMaterial().setFloat4("baseColor",new Color(1.0f,1.0f,0.0f,1.0f));

            }
            scafa2.select();
        }
        if(selected==3){
            TransformableNode scafal=new TransformableNode(arFragment.getTransformationSystem());
            scafal.setParent(anchorNode);
            // cal.setLocalPosition();
            scafal.setRenderable(scafaRendabila_l);
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Alb") {
                scafaRendabila_l.getMaterial().setFloat4("baseColor",new Color(1.0f,1.0f,1.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Verde") {
                scafaRendabila_l.getMaterial().setFloat4("baseColor",new Color(0.0f,1.0f,0.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Mov") {
                scafaRendabila_l.getMaterial().setFloat4("baseColor",new Color(1.0f,0.0f,1.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Albastru") {
                scafaRendabila_l.getMaterial().setFloat4("baseColor",new Color(0.0f,0.0f,1.0f,1.0f));

            }
            if (ScafeFragment.sColorChoice.getSelectedItem() == "Portocaliu") {
                scafaRendabila_l.getMaterial().setFloat4("baseColor",new Color(1.0f,1.0f,0.0f,1.0f));

            }
            scafal.select();
        }
    }


    private void setArrayView() {
        arrayView=new View[]{ scafa,scafa2,scafal};
    }
    private void setClickListener() {
        for(int i=0;i<arrayView.length;i++){
            arrayView[i].setOnClickListener(this);
        }
    }


    private void onSceneUpdate(Frame frameTime) {
        try {
            Frame currentFrame = arFragment.getArSceneView().getArFrame();
            Image currentImage = currentFrame.acquireCameraImage();
            int imageFormat = currentImage.getFormat();
            if (imageFormat == ImageFormat.YUV_420_888) {
                Log.d("ImageFormat", "Image format is YUV_420_888");
            }
        } catch (NotYetAvailableException e) {
            e.printStackTrace();
        }
    }
    private static byte[] NV21toJPEG(byte[] nv21, int width, int height) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        YuvImage yuv = new YuvImage(nv21, ImageFormat.YUV_420_888, width, height, null);
        yuv.compressToJpeg(new Rect(0,0,width,height), 100, out);
        return out.toByteArray();
    }

    public static void WriteImageInformation(Image image, String path) throws IOException {
        byte[] data = null;
        data = NV21toJPEG(YUV_420_888toNV21(image),
                image.getWidth(), image.getHeight());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
        bos.write(data);
        bos.flush();
        bos.close();
    }

    private static byte[] YUV_420_888toNV21(Image image) {
        byte[] nv21;
        ByteBuffer yBuffer = image.getPlanes()[0].getBuffer();
        ByteBuffer uBuffer = image.getPlanes()[1].getBuffer();
        ByteBuffer vBuffer = image.getPlanes()[2].getBuffer();

        int ySize = yBuffer.remaining();
        int uSize = uBuffer.remaining();
        int vSize = vBuffer.remaining();

        nv21 = new byte[ySize + uSize + vSize];

        //U and V are swapped
        yBuffer.get(nv21, 0, ySize);
        vBuffer.get(nv21, ySize, vSize);
        uBuffer.get(nv21, ySize + vSize, uSize);

        return nv21;
    }

        private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        // If there is no frame, just return.
        if (frame == null) {
            return;
        }

        Collection<AugmentedImage> updatedAugmentedImages =
                frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage : updatedAugmentedImages) {
            switch (augmentedImage.getTrackingState()) {
                case PAUSED:
                    // When an image is in PAUSED state, but the camera is not PAUSED, it has been detected,
                    // but not yet tracked.
                    String text = "Detected Image " + augmentedImage.getIndex();
                    SnackbarHelper.getInstance().showMessage(this, text);
                    break;

                case TRACKING:
                    // Have to switch to UI Thread to update View.
                    fitToScanView.setVisibility(View.GONE);

                    // Create a new anchor for newly found images.
                    if (!augmentedImageMap.containsKey(augmentedImage)) {
                        AugmentedImageNode node = new AugmentedImageNode(this);
                        node.setImage(augmentedImage);
                        augmentedImageMap.put(augmentedImage, node);
                        arFragment.getArSceneView().getScene().addChild(node);
                    }
                    break;

                case STOPPED:
                    augmentedImageMap.remove(augmentedImage);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.s1){
            selected=1;
        }else{
            if(v.getId()==R.id.s2){
                selected=2;
            }else{
                selected=3;
            }
        }
    }
}
