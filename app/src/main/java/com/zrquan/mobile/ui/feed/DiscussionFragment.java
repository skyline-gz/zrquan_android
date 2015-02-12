package com.zrquan.mobile.ui.feed;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zrquan.mobile.R;
import com.viewpagerindicator.CirclePageIndicator;

import com.zrquan.mobile.ZrquanApplication;
import com.zrquan.mobile.controller.DiscussionController;
import com.zrquan.mobile.event.PullDownEvent;
import com.zrquan.mobile.event.PullUpEvent;
import com.zrquan.mobile.model.Account;
import com.zrquan.mobile.model.Discussion;
import com.zrquan.mobile.support.util.ScreenUtils;
import com.zrquan.mobile.support.util.UrlUtils;
import com.zrquan.mobile.ui.common.CommonFragment;
import com.zrquan.mobile.widget.pulltorefresh.PullToRefreshBase;
import com.zrquan.mobile.widget.pulltorefresh.PullToRefreshListView;
import com.zrquan.mobile.widget.viewpager.AutoScrollViewPager;
import com.zrquan.mobile.widget.viewpager.ImagePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;

//轻讨论 动态
public class DiscussionFragment extends CommonFragment {
    private Context context;
    private AutoScrollViewPager vpBanner;
    private CirclePageIndicator indicatorBanner;
    private ImageView ivCancelBanner;
    private RelativeLayout rlBanner;

    private Integer[] discussionIds;
    private int pullUpCounter = 0;

    private ListView mListView;
    private Parcelable mListViewState;                   //用于保存ListView状态
    private PullToRefreshListView mPullListView;
    private ArrayAdapter<String> mAdapter;
    private LinkedList<String> mListItems;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private boolean mIsStart = true;
    private int mCurIndex = 0;
    private static final int mLoadDataCount = 100;

    private int index;
//    private ScrollControlReceiver scrollControlReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        if (mPullListView == null) {
            context = getActivity().getApplicationContext();

            View bannerView = getBannerView(inflater, container);
            // http://stackoverflow.com/questions/4393775/android-classcastexception-when-adding-a-header-view-to-expandablelistview
            // ERROR/AndroidRuntime(421): Caused by:java.lang.ClassCastException: android.widget.LinearLayout$LayoutParams
            // 修复因HeadView不是ListView导致的运行时异常
            // So basically, if you are adding a view to another,
            // you MUST set the LayoutParams of the view to the LayoutParams type that the parent uses,
            // or you will get a runtime error.
            bannerView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                    ListView.LayoutParams.WRAP_CONTENT));

            mPullListView = new PullToRefreshListView(context);

            mPullListView.setBackgroundColor(getResources().getColor(R.color.main_feed_background_color));
            mCurIndex = mLoadDataCount;
            mListItems = new LinkedList<String>();
            mListItems.addAll(Arrays.asList(mStrings).subList(0, mCurIndex));
            mAdapter = new ArrayAdapter<String>(context, R.layout.layout_discussion_card_item, R.id.line1, mListItems);
            mListView = mPullListView.getRefreshableView();

            // Note: When first introduced, this method could only be called before
            // setting the adapter with {@link #setAdapter(ListAdapter)}. Starting with
            mListView.addHeaderView(bannerView);
            mListView.setAdapter(mAdapter);
            mListView.setDivider(null);
            mListView.setDividerHeight((int) ScreenUtils.dpToPx(context, 6.0f));
            mListView.setSelector(android.R.color.transparent);
            mListView.setCacheColorHint(Color.TRANSPARENT);

            mPullListView.setPullLoadEnabled(false);
            mPullListView.setScrollLoadEnabled(true);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                    String text = mListItems.get(position) + ", index = " + (position + 1);
                    Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                }
            });
            mPullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    mIsStart = true;

                    Account account = ZrquanApplication.getInstance().getAccount();
                    if (account != null && account.getId() != null) {
                        DiscussionController.getIdsAndInitialList(account.getId(), UrlUtils.SORT_TYPE_DEFAULT);
//                        DiscussionController.getIdsAndInitialList(10);
                    } else {
                        mAdapter.notifyDataSetChanged();
                        mPullListView.onPullDownRefreshComplete();
                    }
                    new GetDataTask().execute();
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    mIsStart = false;
                    pullUpCounter++;
                    Account account = ZrquanApplication.getInstance().getAccount();
                    if (account != null && account.getId() != null) {
                        Integer[] partialIds = Arrays.copyOfRange(
                                discussionIds, pullUpCounter * 20, pullUpCounter * 20 + 20);
                        DiscussionController.getPartialList(partialIds, UrlUtils.SORT_TYPE_DEFAULT);
                    }
                }
            });
            setLastUpdateTime();
        } else {
            ((ViewGroup) mPullListView.getParent()).removeView(mPullListView);
            // Restore previous state (including selected item index and scroll position)
            mListView.onRestoreInstanceState(mListViewState);
        }

        vpBanner.startAutoScroll();

        return mPullListView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // see http://stackoverflow.com/questions/3014089/maintain-save-restore-scroll-position-when-returning-to-a-listview/3035521#3035521
        // Save ListView state
        mListViewState = mListView.onSaveInstanceState();
        EventBus.getDefault().unregister(this);
        vpBanner.stopAutoScroll();
    }

    private View getBannerView(LayoutInflater inflater, ViewGroup container) {
        View v = inflater.inflate(R.layout.banner_feed, container, false);
        rlBanner = (RelativeLayout) v.findViewById(R.id.layout_banner);
        vpBanner = (AutoScrollViewPager) v.findViewById(R.id.view_pager_banner);
        indicatorBanner = (CirclePageIndicator) v.findViewById(R.id.indicator_banner);
        ivCancelBanner = (ImageView) v.findViewById(R.id.image_view_cancel_banner);

        List<Integer> imageIdList = new ArrayList<Integer>();
        imageIdList.add(R.drawable.banner1);
        imageIdList.add(R.drawable.banner2);
        imageIdList.add(R.drawable.banner3);
        imageIdList.add(R.drawable.banner4);
        vpBanner.setAdapter(new ImagePagerAdapter(context, imageIdList));
        indicatorBanner.setViewPager(vpBanner);

        ivCancelBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation slideUpAndFadeOut = AnimationUtils.loadAnimation(context, R.anim.anim_slide_up);

                slideUpAndFadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // Called when the Animation starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Called when the Animation ended
                        // Since we are fading a View out we set the visibility
                        // to GONE once the Animation is finished
                        rlBanner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // This is called each time the Animation repeats
                    }
                });

                rlBanner.startAnimation(slideUpAndFadeOut);
            }
        });

        vpBanner.setInterval(2000);
        //是否允许切换到最后一个banner时，切换到另一个fragment
        //viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);

        return v;
    }

    public void onEvent(PullDownEvent event){
        mListItems.clear();
        pullUpCounter = 0;      //重置 pullUpCounter
        discussionIds = event.getDiscussionIds();

        List<Discussion> dList = event.getInitialDiscussionList();
        for (int i = 0; i < dList.size(); i++) {
            Discussion discussion = dList.get(i);
            mListItems.addLast(discussion.getPostContent() +
                    discussion.getPostUserName() + discussion.getThemeName());
        }
        mAdapter.notifyDataSetChanged();
        mPullListView.onPullDownRefreshComplete();

        mPullListView.setHasMoreData(true);
        setLastUpdateTime();
    }

    public void onEvent(PullUpEvent event){
        List<Discussion> dList = event.getPartialList();
        for (int i = 0; i < dList.size(); i++) {
            Discussion discussion = dList.get(i);
            mListItems.addLast(discussion.getPostContent() +
                    discussion.getPostUserName() + discussion.getThemeName());
        }
        mAdapter.notifyDataSetChanged();
        mPullListView.onPullUpRefreshComplete();

        mPullListView.setHasMoreData(true);
        setLastUpdateTime();
    }

    public void onEvent(ScrollBannerEvent scrollBannerEvent) {
        if(scrollBannerEvent.enableScroll) {
            vpBanner.startAutoScroll();
        } else {
            vpBanner.stopAutoScroll();
        }
    }

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            return null;
        }

//        @Override
//        protected void onPostExecute(Void result) {
//            boolean hasMoreData = true;
//            if (mIsStart) {
//                mListItems.addFirst("Added after refresh...");
//            } else {
//                int start = mCurIndex;
//                int end = mCurIndex + mLoadDataCount;
//                if (end >= mStrings.length) {
//                    end = mStrings.length;
//                    hasMoreData = false;
//                }
//
//                for (int i = start; i < end; ++i) {
//                    mListItems.add(mStrings[i]);
//                }
//
//                mCurIndex = end;
//            }
//
//            mAdapter.notifyDataSetChanged();
//            mPullListView.onPullDownRefreshComplete();
//            mPullListView.onPullUpRefreshComplete();
//            mPullListView.setHasMoreData(hasMoreData);
//            setLastUpdateTime();
//
//            super.onPostExecute(result);
//        }
    }

    private void setLastUpdateTime() {
        String text = formatDateTime(System.currentTimeMillis());
        mPullListView.setLastUpdatedLabel(text);
    }

    private String formatDateTime(long time) {
        if (0 == time) {
            return "";
        }

        return mDateFormat.format(new Date(time));
    }

    public static final String[] mStrings = {
            "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
            "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale",
            "Aisy Cendre", "Allgauer Emmentaler", "Alverca", "Ambert", "American Cheese",
            "Ami du Chambertin", "Anejo Enchilado", "Anneau du Vic-Bilh", "Anthoriro", "Appenzell",
            "Aragon", "Ardi Gasna", "Ardrahan", "Armenian String", "Aromes au Gene de Marc",
            "Asadero", "Asiago", "Aubisque Pyrenees", "Autun", "Avaxtskyr", "Baby Swiss",
            "Babybel", "Baguette Laonnaise", "Bakers", "Baladi", "Balaton", "Bandal", "Banon",
            "Barry's Bay Cheddar", "Basing", "Basket Cheese", "Bath Cheese", "Bavarian Bergkase",
            "Baylough", "Beaufort", "Beauvoorde", "Beenleigh Blue", "Beer Cheese", "Bel Paese",
            "Bergader", "Bergere Bleue", "Berkswell", "Beyaz Peynir", "Bierkase", "Bishop Kennedy",
            "Blarney", "Bleu d'Auvergne", "Bleu de Gex", "Bleu de Laqueuille",
            "Bleu de Septmoncel", "Bleu Des Causses", "Blue", "Blue Castello", "Blue Rathgore",
            "Blue Vein (Australian)", "Blue Vein Cheeses", "Bocconcini", "Bocconcini (Australian)",
            "Boeren Leidenkaas", "Bonchester", "Bosworth", "Bougon", "Boule Du Roves",
            "Boulette d'Avesnes", "Boursault", "Boursin", "Bouyssou", "Bra", "Braudostur",
            "Breakfast Cheese", "Brebis du Lavort", "Brebis du Lochois", "Brebis du Puyfaucon",
            "Bresse Bleu", "Brick", "Brie", "Brie de Meaux", "Brie de Melun", "Brillat-Savarin",
            "Brin", "Brin d' Amour", "Brin d'Amour", "Brinza (Burduf Brinza)",
            "Briquette de Brebis", "Briquette du Forez", "Broccio", "Broccio Demi-Affine",
            "Brousse du Rove", "Bruder Basil", "Brusselae Kaas (Fromage de Bruxelles)", "Bryndza",
            "Buchette d'Anjou", "Buffalo", "Burgos", "Butte", "Butterkase", "Button (Innes)",
            "Buxton Blue", "Cabecou", "Caboc", "Cabrales", "Cachaille", "Caciocavallo", "Caciotta",
            "Caerphilly", "Cairnsmore", "Calenzana", "Cambazola", "Camembert de Normandie",
            "Canadian Cheddar", "Canestrato", "Cantal", "Caprice des Dieux", "Capricorn Goat",
            "Capriole Banon", "Carre de l'Est", "Casciotta di Urbino", "Cashel Blue", "Castellano",
            "Castelleno", "Castelmagno", "Castelo Branco", "Castigliano", "Cathelain",
            "Celtic Promise", "Cendre d'Olivet", "Cerney", "Chabichou", "Chabichou du Poitou",
            "Chabis de Gatine", "Chaource", "Charolais", "Chaumes", "Cheddar",
            "Cheddar Clothbound", "Cheshire", "Chevres", "Chevrotin des Aravis", "Chontaleno",
            "Civray", "Coeur de Camembert au Calvados", "Coeur de Chevre", "Colby", "Cold Pack",
            "Comte", "Coolea", "Cooleney", "Coquetdale", "Corleggy", "Cornish Pepper",
            "Cotherstone", "Cotija", "Cottage Cheese", "Cottage Cheese (Australian)",
            "Cougar Gold", "Coulommiers", "Coverdale", "Crayeux de Roncq", "Cream Cheese",
            "Cream Havarti", "Crema Agria", "Crema Mexicana", "Creme Fraiche", "Crescenza",
            "Croghan", "Crottin de Chavignol", "Crottin du Chavignol", "Crowdie", "Crowley",
            "Cuajada", "Curd", "Cure Nantais", "Curworthy", "Cwmtawe Pecorino",
            "Cypress Grove Chevre", "Danablu (Danish Blue)", "Danbo", "Danish Fontina",
            "Daralagjazsky", "Dauphin", "Delice des Fiouves", "Denhany Dorset Drum", "Derby",
            "Dessertnyj Belyj", "Devon Blue", "Devon Garland", "Dolcelatte", "Doolin",
            "Doppelrhamstufel", "Dorset Blue Vinney", "Double Gloucester", "Double Worcester",
            "Dreux a la Feuille", "Dry Jack", "Duddleswell", "Dunbarra", "Dunlop", "Dunsyre Blue",
            "Duroblando", "Durrus", "Dutch Mimolette (Commissiekaas)", "Edam", "Edelpilz",
            "Emental Grand Cru", "Emlett", "Emmental", "Epoisses de Bourgogne", "Esbareich",
            "Esrom", "Etorki", "Evansdale Farmhouse Brie", "Evora De L'Alentejo", "Exmoor Blue",
            "Explorateur", "Feta", "Feta (Australian)", "Figue", "Filetta", "Fin-de-Siecle",
            "Finlandia Swiss", "Finn", "Fiore Sardo", "Fleur du Maquis", "Flor de Guia",
            "Flower Marie", "Folded", "Folded cheese with mint", "Fondant de Brebis",
            "Fontainebleau", "Fontal", "Fontina Val d'Aosta", "Formaggio di capra", "Fougerus",
            "Four Herb Gouda", "Fourme d' Ambert", "Fourme de Haute Loire", "Fourme de Montbrison",
            "Fresh Jack", "Fresh Mozzarella", "Fresh Ricotta", "Fresh Truffles", "Fribourgeois",
            "Friesekaas", "Friesian", "Friesla", "Frinault", "Fromage a Raclette", "Fromage Corse",
            "Fromage de Montagne de Savoie", "Fromage Frais", "Fruit Cream Cheese",
            "Frying Cheese", "Fynbo", "Gabriel", "Galette du Paludier", "Galette Lyonnaise",
            "Galloway Goat's Milk Gems", "Gammelost", "Gaperon a l'Ail", "Garrotxa", "Gastanberra",
            "Geitost", "Gippsland Blue", "Gjetost", "Gloucester", "Golden Cross", "Gorgonzola",
            "Gornyaltajski", "Gospel Green", "Gouda", "Goutu", "Gowrie", "Grabetto", "Graddost",
            "Grafton Village Cheddar", "Grana", "Grana Padano", "Grand Vatel",
            "Grataron d' Areches", "Gratte-Paille", "Graviera", "Greuilh", "Greve",
            "Gris de Lille", "Gruyere", "Gubbeen", "Guerbigny", "Halloumi",
            "Halloumy (Australian)", "Haloumi-Style Cheese", "Harbourne Blue", "Havarti",
            "Heidi Gruyere", "Hereford Hop", "Herrgardsost", "Herriot Farmhouse", "Herve",
            "Hipi Iti", "Hubbardston Blue Cow", "Hushallsost", "Iberico", "Idaho Goatster",
            "Idiazabal", "Il Boschetto al Tartufo", "Ile d'Yeu", "Isle of Mull", "Jarlsberg",
            "Jermi Tortes", "Jibneh Arabieh", "Jindi Brie", "Jubilee Blue", "Juustoleipa",
            "Kadchgall", "Kaseri", "Kashta", "Kefalotyri", "Kenafa", "Kernhem", "Kervella Affine",
            "Kikorangi", "King Island Cape Wickham Brie", "King River Gold", "Klosterkaese",
            "Knockalara", "Kugelkase", "L'Aveyronnais", "L'Ecir de l'Aubrac", "La Taupiniere",
            "La Vache Qui Rit", "Laguiole", "Lairobell", "Lajta", "Lanark Blue", "Lancashire",
            "Langres", "Lappi", "Laruns", "Lavistown", "Le Brin", "Le Fium Orbo", "Le Lacandou",
            "Le Roule", "Leafield", "Lebbene", "Leerdammer", "Leicester", "Leyden", "Limburger",
            "Lincolnshire Poacher", "Lingot Saint Bousquet d'Orb", "Liptauer", "Little Rydings",
            "Livarot", "Llanboidy", "Llanglofan Farmhouse", "Loch Arthur Farmhouse",
            "Loddiswell Avondale", "Longhorn", "Lou Palou", "Lou Pevre", "Lyonnais", "Maasdam",
            "Macconais", "Mahoe Aged Gouda", "Mahon", "Malvern", "Mamirolle", "Manchego",
            "Manouri", "Manur", "Marble Cheddar", "Marbled Cheeses", "Maredsous", "Margotin",
            "Maribo", "Maroilles", "Mascares", "Mascarpone", "Mascarpone (Australian)",
            "Mascarpone Torta", "Matocq", "Maytag Blue", "Meira", "Menallack Farmhouse",
            "Menonita", "Meredith Blue", "Mesost", "Metton (Cancoillotte)", "Meyer Vintage Gouda",
            "Mihalic Peynir", "Milleens", "Mimolette", "Mine-Gabhar", "Mini Baby Bells", "Mixte",
            "Molbo", "Monastery Cheeses", "Mondseer", "Mont D'or Lyonnais", "Montasio",
            "Monterey Jack", "Monterey Jack Dry", "Morbier", "Morbier Cru de Montagne",
            "Mothais a la Feuille", "Mozzarella", "Mozzarella (Australian)",
            "Mozzarella di Bufala", "Mozzarella Fresh, in water", "Mozzarella Rolls", "Munster",
            "Murol", "Mycella", "Myzithra", "Naboulsi", "Nantais", "Neufchatel",
            "Neufchatel (Australian)", "Niolo", "Nokkelost", "Northumberland", "Oaxaca",
            "Olde York", "Olivet au Foin", "Olivet Bleu", "Olivet Cendre",
            "Orkney Extra Mature Cheddar", "Orla", "Oschtjepka", "Ossau Fermier", "Ossau-Iraty",
            "Oszczypek", "Oxford Blue", "P'tit Berrichon", "Palet de Babligny", "Paneer", "Panela",
            "Pannerone", "Pant ys Gawn", "Parmesan (Parmigiano)", "Parmigiano Reggiano",
            "Pas de l'Escalette", "Passendale", "Pasteurized Processed", "Pate de Fromage",
            "Patefine Fort", "Pave d'Affinois", "Pave d'Auge", "Pave de Chirac", "Pave du Berry",
            "Pecorino", "Pecorino in Walnut Leaves", "Pecorino Romano", "Peekskill Pyramid",
            "Pelardon des Cevennes", "Pelardon des Corbieres", "Penamellera", "Penbryn",
            "Pencarreg", "Perail de Brebis", "Petit Morin", "Petit Pardou", "Petit-Suisse",
            "Picodon de Chevre", "Picos de Europa", "Piora", "Pithtviers au Foin",
            "Plateau de Herve", "Plymouth Cheese", "Podhalanski", "Poivre d'Ane", "Polkolbin",
            "Pont l'Eveque", "Port Nicholson", "Port-Salut", "Postel", "Pouligny-Saint-Pierre",
            "Pourly", "Prastost", "Pressato", "Prince-Jean", "Processed Cheddar", "Provolone",
            "Provolone (Australian)", "Pyengana Cheddar", "Pyramide", "Quark",
            "Quark (Australian)", "Quartirolo Lombardo", "Quatre-Vents", "Quercy Petit",
            "Queso Blanco", "Queso Blanco con Frutas --Pina y Mango", "Queso de Murcia",
            "Queso del Montsec", "Queso del Tietar", "Queso Fresco", "Queso Fresco (Adobera)",
            "Queso Iberico", "Queso Jalapeno", "Queso Majorero", "Queso Media Luna",
            "Queso Para Frier", "Queso Quesadilla", "Rabacal", "Raclette", "Ragusano", "Raschera",
            "Reblochon", "Red Leicester", "Regal de la Dombes", "Reggianito", "Remedou",
            "Requeson", "Richelieu", "Ricotta", "Ricotta (Australian)", "Ricotta Salata", "Ridder",
            "Rigotte", "Rocamadour", "Rollot", "Romano", "Romans Part Dieu", "Roncal", "Roquefort",
            "Roule", "Rouleau De Beaulieu", "Royalp Tilsit", "Rubens", "Rustinu", "Saaland Pfarr",
            "Saanenkaese", "Saga", "Sage Derby", "Sainte Maure", "Saint-Marcellin",
            "Saint-Nectaire", "Saint-Paulin", "Salers", "Samso", "San Simon", "Sancerre",
            "Sap Sago", "Sardo", "Sardo Egyptian", "Sbrinz", "Scamorza", "Schabzieger", "Schloss",
            "Selles sur Cher", "Selva", "Serat", "Seriously Strong Cheddar", "Serra da Estrela",
            "Sharpam", "Shelburne Cheddar", "Shropshire Blue", "Siraz", "Sirene", "Smoked Gouda",
            "Somerset Brie", "Sonoma Jack", "Sottocenare al Tartufo", "Soumaintrain",
            "Sourire Lozerien", "Spenwood", "Sraffordshire Organic", "St. Agur Blue Cheese",
            "Stilton", "Stinking Bishop", "String", "Sussex Slipcote", "Sveciaost", "Swaledale",
            "Sweet Style Swiss", "Swiss", "Syrian (Armenian String)", "Tala", "Taleggio", "Tamie",
            "Tasmania Highland Chevre Log", "Taupiniere", "Teifi", "Telemea", "Testouri",
            "Tete de Moine", "Tetilla", "Texas Goat Cheese", "Tibet", "Tillamook Cheddar",
            "Tilsit", "Timboon Brie", "Toma", "Tomme Brulee", "Tomme d'Abondance",
            "Tomme de Chevre", "Tomme de Romans", "Tomme de Savoie", "Tomme des Chouans", "Tommes",
            "Torta del Casar", "Toscanello", "Touree de L'Aubier", "Tourmalet",
            "Trappe (Veritable)", "Trois Cornes De Vendee", "Tronchon", "Trou du Cru", "Truffe",
            "Tupi", "Turunmaa", "Tymsboro", "Tyn Grug", "Tyning", "Ubriaco", "Ulloa",
            "Vacherin-Fribourgeois", "Valencay", "Vasterbottenost", "Venaco", "Vendomois",
            "Vieux Corse", "Vignotte", "Vulscombe", "Waimata Farmhouse Blue",
            "Washed Rind Cheese (Australian)", "Waterloo", "Weichkaese", "Wellington",
            "Wensleydale", "White Stilton", "Whitestone Farmhouse", "Wigmore", "Woodside Cabecou",
            "Xanadu", "Xynotyro", "Yarg Cornish", "Yarra Valley Pyramid", "Yorkshire Blue",
            "Zamorano", "Zanetti Grana Padano", "Zanetti Parmigiano Reggiano"
    };

    //此事件仅在切换feedFragment的Viewpager时使用，用于暂停，开启滚动
    public static class ScrollBannerEvent {
        public boolean enableScroll = false;
        public ScrollBannerEvent (boolean enableScroll){
            this.enableScroll = enableScroll;
        }
    }
}
