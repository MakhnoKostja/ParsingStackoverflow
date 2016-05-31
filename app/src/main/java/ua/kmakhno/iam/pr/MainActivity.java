package ua.kmakhno.iam.pr;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(myListener);
    }
    private ProgressDialog progressDialog;
    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            progressDialog = ProgressDialog.show(MainActivity.this, "Working...", "request to server", true, false);
            new ParseSite().execute("http://www.stackoverflow.com");
        }
    };

    private class ParseSite extends AsyncTask<String, Void, List<String>>{

        @Override
        protected List<String> doInBackground(String... params) {

            List<String> output = new ArrayList<>();
                try {
                    HtmlHelper hh = new HtmlHelper(new URL(params[0]));
                    List<TagNode> links = hh.getLinksByClass("question-hyperlink");

                    for (Iterator<TagNode> iterator = links.iterator(); iterator.hasNext();)
                    {
                        TagNode divElement = (TagNode) iterator.next();
                        output.add(divElement.getText().toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return output;

        }

        @Override
        protected void onPostExecute(List<String> strings) {

            progressDialog.dismiss();

            ListView listView = (ListView)findViewById(R.id.listView);

            listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, strings));

        }
    }

}
