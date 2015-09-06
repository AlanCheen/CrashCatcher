package yifeiyuan.library.crashcatcher;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CrashCatcherActivity extends AppCompatActivity {


    private List<Trace> traces;
    private Context mContext ;
    private RecyclerView mRvList;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_main);

        mContext = this;
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        traces.clear();
        loadData();
    }

    protected void init(Bundle savedInstanceState) {

        mAdapter = new ListAdapter();
        traces = new ArrayList<>();
        mRvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRvList.setAdapter(mAdapter);

    }

    private void loadData() {
//        String rootPath = Environment.getExternalStorageDirectory().getPath()+"/CrashCracher/";
        String rootPath = mContext.getCacheDir().getPath()+"/CrashCracher/";

        File file = new File(rootPath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File file1 : files) {
                try {
                    Trace trace = (Trace) Trace.readObject(file1);
                    traces.add(trace);
                } catch (IOException e) {
                    e.printStackTrace();
                    file1.delete();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    file1.delete();
                }
            }
        }else{
            file.mkdirs();
        }
        mAdapter.notifyDataSetChanged();
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.mTvInfo.setText(traces.get(position).trace);
            holder.mTvDate.setText(traces.get(position).date);
        }

        @Override
        public int getItemCount() {
            return traces.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public TextView mTvInfo;
            public TextView mTvDate;
            public ViewHolder(View view){
                super(view);
                mTvInfo = (TextView) view.findViewById(R.id.tv_info);
                mTvDate = (TextView) view.findViewById(R.id.tv_date);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
