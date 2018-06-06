package com.kj;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kj.base.MyBaseActivity;
import com.kj.pojo.RetMsg;
import com.kj.pojo.Xiazai;
import com.kj.pojo.danwei;
import com.kj.tree.Dept;
import com.kj.tree.Node;
import com.kj.tree.NodeHelper;
import com.kj.util.HtmlUtils;
import com.kj.util.HttpUrl;
import com.kj.util.MyApplication;
import com.kj.util.MyToastUtil;
import com.kj.util.NetWorkUtils;
import com.kj.util.Url;
import com.kj.util.UserClient;
import com.kj.view.SlidingMenu;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.litepal.crud.DataSupport;
import org.wlf.filedownloader.DownloadFileInfo;
import org.wlf.filedownloader.FileDownloader;
import org.wlf.filedownloader.listener.OnFileDownloadStatusListener;
import org.wlf.filedownloader.listener.simple.OnSimpleFileDownloadStatusListener;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017-12-15.
 */

public class ActivitySc extends MyBaseActivity {
    private SlidingMenu mSm;
    private ListView mListView;
    List<danwei> list;
    int a=0;
    List<Node> data;
    Context con = ActivitySc.this;
    private NodeTreeAdapter mAdapter;

    String content="";
    private LinkedList<Node> mLinkedList = new LinkedList<>();

    TextView name, time, xzcs, yeshu;
    TextView webView;
    ImageView xiazai;
    String isdown = "0";
    LinearLayout back;
    Button pre, next;

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_sc);
        FileDownloader
                .registerDownloadStatusListener(mOnFileDownloadStatusListener);
        back = (LinearLayout) findViewById(R.id.ddd);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSm = (SlidingMenu) findViewById(R.id.sliding_menu);
        mSm.toggleMenu();
        webView = (TextView) findViewById(R.id.webview);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);
        xzcs = (TextView) findViewById(R.id.xzcs);
        yeshu = (TextView) findViewById(R.id.yeshu);
        xiazai = (ImageView) findViewById(R.id.xiazai);
        mListView = (ListView) findViewById(R.id.id_tree);
        if (NetWorkUtils.isNetworkConnected(con) && MyApplication.getApp().getU() != null)
            getmenu();
        pre = (Button) findViewById(R.id.pre);
        next = (Button) findViewById(R.id.next);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileDownloader.unregisterDownloadStatusListener(mOnFileDownloadStatusListener);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    //获取菜单
    public void getmenu() {
        RequestParams ps = new RequestParams();
        UserClient.post(HttpUrl.GetScTree + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
            @Override
            public void onFailure(Throwable error, String content) {
                super.onFailure(error, content);
                getmenu();
            }

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                System.out.println(content);
                RetMsg ret = JSON.parseObject(content, RetMsg.class);
                if (ret.getCode().equals("0")) {
                    list = JSON.parseArray(ret.getData(), danwei.class);
                    Log.i("tagggg",list.size()+"");
                    mAdapter = new NodeTreeAdapter(con, mListView, mLinkedList);
                    mListView.setAdapter(mAdapter);
                    data = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        data.add(new Dept(list.get(i).getId(), list.get(i).getpId(), list.get(i).getName()));
                    }
//                addOne(data);
                    mLinkedList.addAll(NodeHelper.sortNodes(data));
                    mAdapter.notifyDataSetChanged();



                }
            }
        });
    }

    public class NodeTreeAdapter extends BaseAdapter {

        //大家经常用ArrayList，但是这里为什么要使用LinkedList
        // ，后面大家会发现因为这个list会随着用户展开、收缩某一项而频繁的进行增加、删除元素操作，
        // 因为ArrayList是数组实现的，频繁的增删性能低下，而LinkedList是链表实现的，对于频繁的增删
        //操作性能要比ArrayList好。
        private LinkedList<Node> nodeLinkedList;
        private LayoutInflater inflater;
        private int retract;//缩进值
        private Context context;

        public NodeTreeAdapter(Context context, ListView listView, LinkedList<Node> linkedList) {
            inflater = LayoutInflater.from(context);
            this.context = context;
            nodeLinkedList = linkedList;
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    expandOrCollapse(position);
                }
            });


            //缩进值，大家可以将它配置在资源文件中，从而实现适配
            retract = (int) (context.getResources().getDisplayMetrics().density * 10 + 0.5f);

        }

        /**
         * 展开或收缩用户点击的条目
         *
         * @param position
         */
        public void expandOrCollapse(int position) {
            Node node = nodeLinkedList.get(position);
            if (node != null && !node.isLeaf()) {
                boolean old = node.isExpand();
                if (old) {
                    List<Node> nodeList = node.get_childrenList();
                    int size = nodeList.size();
                    Node tmp = null;
                    for (int i = 0; i < size; i++) {
                        tmp = nodeList.get(i);
                        if (tmp.isExpand()) {
                            collapse(tmp, position + 1);
                        }
                        nodeLinkedList.remove(position + 1);
                    }
                } else {
                    nodeLinkedList.addAll(position + 1, node.get_childrenList());
                }
                node.setIsExpand(!old);
                notifyDataSetChanged();
            } else {
                mSm.toggleMenu();
                //获取手册的内容
                getdes(node.get_id());
            }
        }


        public void getdes(String id) {
            RequestParams ps = new RequestParams();
            ps.add("catalogid", id);
            UserClient.get(HttpUrl.GetScDes + ";JSESSIONID=" + MyApplication.getApp().getU().getSessionid(), ps, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String content) {
                    super.onSuccess(content);
                    System.out.println(content);
                    RetMsg ret = JSON.parseObject(content, RetMsg.class);
                    if (ret.getCode().equals("0")) {
                        final JSONObject j = JSON.parseArray(ret.getData()).getJSONObject(0);
                        name.setText(j.getString("catalogname"));
                        time.setText(j.getString("publicdate"));
                        xzcs.setText("");
                        yeshu.setText("");
                        isdown = j.getString("isdown");
                        String dd=j.getString("content").replace("src=\"","src=\"http://139.224.24.245:7878");
                        Log.i("ccccc",dd);
                        Log.i("dddd",dd.split("<src=\"").length+"");
                        webView.setText(HtmlUtils.getHtml(getApplicationContext(),webView,dd));
                        textHtmlClick(ActivitySc.this,webView);
//                        webView.getSettings().setJavaScriptEnabled(true);
//                        webView.setWebViewClient(new WebViewClient(){
//                            @Override
//                            public boolean shouldOverrideUrlLoading
//                                    (WebView view, String url) {
//                                Log.i("用户单击超连接", url);
//                                FileDownloader.start(Url.urls() + url);
//                                return true;
//                            }
//
//                            @Override
//                            public void onPageFinished(WebView view, String url) {
//                                view.getSettings().setJavaScriptEnabled(true);
//                                Log.i("rrrrrrr","的点点滴滴多多多"+url);
//                                super.onPageFinished(view, url);
//
//
//                            }
//                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                            @Override
//                            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                                Log.i("用户单击超连接1", request.getUrl().toString());
//                                FileDownloader.start(Url.urls() + request.getUrl().toString());
//                                return true;
//                            }
//                        });
//                        content=j.getString("content");
//                        webView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
//                        webView.loadData(j.getString("content"), "text/html;charset=UTF-8", null);
                        pre.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        pre.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (j.getString("prevId") != null && !j.getString("prevId").equals(""))
                                    getdes(j.getString("prevId"));
                            }
                        });
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (j.getString("nextId") != null && !j.getString("nextId").equals(""))
                                    getdes(j.getString("nextId"));
                            }
                        });
                        xiazai.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isdown.equals("0")) {
                                    Xiazai x = new Xiazai();
                                    x.setType("手册");
                                    x.setXid(j.getString("id"));
                                    x.setName(j.getString("catalogname"));
                                    x.setMsg(j.getString("content"));
                                    x.setTime(j.getString("publicdate"));
                                    if (DataSupport.where("xid=?", x.getXid()).find(Xiazai.class).size() == 0) {
                                        x.save();
                                        MyToastUtil.ShowToast(con, "下载成功");
                                    } else {
                                        MyToastUtil.ShowToast(con, "下载过了");
                                    }

                                } else {
                                    MyToastUtil.ShowToast(con, "没有权限");
                                }
                            }
                        });


                    }
                }
            });
        }


        /**
         * 递归收缩用户点击的条目
         * 因为此中实现思路是：当用户展开某一条时，就将该条对应的所有子节点加入到nodeLinkedList
         * ，同时控制缩进，当用户收缩某一条时，就将该条所对应的子节点全部删除，而当用户跨级缩进时
         * ，就需要递归缩进其所有的孩子节点，这样才能保持整个nodeLinkedList的正确性，同时这种实
         * 现方式避免了每次对所有数据进行处理然后插入到一个list，最后显示出来，当数据量一大，就会卡顿，
         * 所以这种只改变局部数据的方式性能大大提高。
         *
         * @param position
         */
        private void collapse(Node node, int position) {
            node.setIsExpand(false);
            List<Node> nodes = node.get_childrenList();
            int size = nodes.size();
            Node tmp = null;
            for (int i = 0; i < size; i++) {
                tmp = nodes.get(i);
                if (tmp.isExpand()) {
                    collapse(tmp, position + 1);
                }
                nodeLinkedList.remove(position + 1);
            }
        }

        @Override
        public int getCount() {
            return nodeLinkedList.size();
        }

        @Override
        public Object getItem(int position) {
            return nodeLinkedList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final com.kj.tree.NodeTreeAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tree_listview_item, null);
                holder = new com.kj.tree.NodeTreeAdapter.ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
                holder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
                holder.confirm = (LinearLayout) convertView.findViewById(R.id.id_confirm);
                convertView.setTag(holder);
            } else {
                holder = (com.kj.tree.NodeTreeAdapter.ViewHolder) convertView.getTag();
            }
            final Node node = nodeLinkedList.get(position);
            holder.label.setText(node.get_label());
            if (node.get_icon() == -1) {
                holder.imageView.setVisibility(View.INVISIBLE);
            } else {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setImageResource(node.get_icon());
            }
            holder.confirm.setTag(position);
            convertView.setPadding(node.get_level() * retract, 5, 5, 5);
            return convertView;
        }

        class ViewHolder {
            public ImageView imageView;
            public TextView label;
            public LinearLayout confirm;
        }

    }

    private final OnFileDownloadStatusListener mOnFileDownloadStatusListener = new OnSimpleFileDownloadStatusListener() {
        @Override
        public void onFileDownloadStatusRetrying(
                DownloadFileInfo downloadFileInfo, int retryTimes) {
            // 正在重试下载（如果你配置了重试次数，当一旦下载失败时会尝试重试下载），retryTimes是当前第几次重试
            System.out.println("重试下载");
        }

        @Override
        public void onFileDownloadStatusWaiting(
                DownloadFileInfo downloadFileInfo) {
            // 等待下载（等待其它任务执行完成，或者FileDownloader在忙别的操作）
            System.out.println("等待下载");
        }

        @Override
        public void onFileDownloadStatusPreparing(
                DownloadFileInfo downloadFileInfo) {
            // 准备中（即，正在连接资源）
            System.out.println("准备中下载");
        }

        @Override
        public void onFileDownloadStatusPrepared(
                DownloadFileInfo downloadFileInfo) {
            // 已准备好（即，已经连接到了资源）
            System.out.println("准备下载");
        }

        @Override
        public void onFileDownloadStatusDownloading(
                DownloadFileInfo downloadFileInfo, float downloadSpeed,
                long remainingTime) {
            // 正在下载，downloadSpeed为当前下载速度，单位KB/s，remainingTime为预估的剩余时间，单位秒
        }

        @Override
        public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
            // 下载已被暂停
            System.out.println("暂停下载");

        }

        @Override
        public void onFileDownloadStatusCompleted(
                final DownloadFileInfo downloadFileInfo) {
            // 下载完成（整个文件已经全部下载完成）
            // 需要判断文件是否存在
            System.out.println("完成下载");
            String videopath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator
                    + "FileDownloader/"
                    + downloadFileInfo.getFileName();
            startActivity(new Intent(con, ActivityYulan.class).putExtra("url", videopath).putExtra("dd","dd"));
        }

        @Override
        public void onFileDownloadStatusFailed(String url,
                                               DownloadFileInfo downloadFileInfo,
                                               FileDownloadStatusFailReason failReason) {
            System.out.println("失败下载");
            // 下载失败了，详细查看失败原因failReason，有些失败原因你可能必须关心
            String failType = failReason.getType();
            String failUrl = failReason.getUrl();// 或：failUrl =
            // url，url和failReason.getUrl()会是一样的

            if (FileDownloadStatusFailReason.TYPE_URL_ILLEGAL.equals(failType)) {
                // 下载failUrl时出现url错误
            } else if (FileDownloadStatusFailReason.TYPE_STORAGE_SPACE_IS_FULL
                    .equals(failType)) {
                // 下载failUrl时出现本地存储空间不足
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_DENIED
                    .equals(failType)) {
                // 下载failUrl时出现无法访问网络
            } else if (FileDownloadStatusFailReason.TYPE_NETWORK_TIMEOUT
                    .equals(failType)) {
                // 下载failUrl时出现连接超时
            } else {
                // 更多错误....
            }

            // 查看详细异常信息
            Throwable failCause = failReason.getCause();// 或：failReason.getOriginalCause()

            // 查看异常描述信息
            String failMsg = failReason.getMessage();// 或：failReason.getOriginalCause().getMessage()
            System.out.println(failMsg);
        }
    };
    /**
     * 处理html文本超链接点击事件
     * @param context
     * @param tv
     */
    public static void textHtmlClick(Context context, TextView tv) {
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = tv.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) text;
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();// should clear old spans
            for (URLSpan url : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(url.getURL(), context);
                style.setSpan(myURLSpan, sp.getSpanStart(url),
                        sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            tv.setText(style);
        }
    }
    private static class MyURLSpan extends ClickableSpan {
        private String mUrl;
        private Context mContext;

        MyURLSpan(String url, Context context) {
            mContext = context;
            mUrl = url;
        }

        @Override
        public void onClick(View widget) {
            FileDownloader.start(Url.urls() + mUrl);
        }
    }
}
