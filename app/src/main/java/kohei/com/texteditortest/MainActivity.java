package kohei.com.texteditortest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jmedeisis.draglinearlayout.DragLinearLayout;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button h1Button = (Button) findViewById(R.id.h1);
        assert h1Button != null;
        h1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edit1);
                String s = editText.getText().toString();
                editText.setText(Html.fromHtml("<h2>" + s + "</h2>"));
            }
        });

        Button rightButton = (Button) findViewById(R.id.right);
        assert rightButton != null;
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.edit2);
                editText.setGravity(Gravity.RIGHT);
            }
        });
        final Button imageButton = (Button) findViewById(R.id.image);
        assert imageButton != null;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    AsyncDownload asyncTask = new AsyncDownload(MainActivity.this);
                    asyncTask.execute("https://www.pakutaso.com/shared/img/thumb/HIRA85_gussurineteiruosuneko_TP_V.jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void setResultImage(Bitmap bmp) {
        DragLinearLayout layout = (DragLinearLayout) findViewById(R.id.linear);
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageBitmap(bmp);
        layout.addView(imageView);
        layout.setViewDraggable(imageView, imageView);

        EditText editText = (EditText) findViewById(R.id.edit1);
        layout.setViewDraggable(editText, editText);
        EditText editText2 = new EditText(this);
        editText2.setId(R.id.edit2);
        layout.addView(editText2);
        layout.setViewDraggable(editText2, editText2);
    }

    private class AsyncDownload extends AsyncTask<String, Integer, Bitmap> {

        private MainActivity mainActivity;

        // コンストラクター
        public AsyncDownload(MainActivity activity){
            mainActivity = activity;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
                InputStream inputStream = url.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                return bitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mainActivity.setResultImage(result);
        }
    }
}
