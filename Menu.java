package c.baloncesto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button stats, info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        info = (Button) findViewById(R.id.button2);
        stats = (Button) findViewById(R.id.button3);


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent().setClass(
                        Menu.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
