package com.example.reychristian.bookrightback;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BorrowedBooksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BorrowedBooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BorrowedBooksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private BooksAdapter adapter;
    private ArrayList<Book> booksList;

    private OnFragmentInteractionListener mListener;

    public BorrowedBooksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BorrowedBooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BorrowedBooksFragment newInstance(String param1, String param2) {
        BorrowedBooksFragment fragment = new BorrowedBooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_borrowed_books, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        booksList = new ArrayList<>();
//        adapter = new BooksAdapter(getActivity(), booksList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

//        prepareBooks();

        return view;
    }

    /**
     * Adding few books for testing
     */
//    private void prepareBooks() {
//        int[] covers = new int[]{
//                R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher};
//
//        booksList.add(new Book(6, "The Notebook", "James Dashner", "Mystery", "Dashner Productions", new Date(2017, 1, 1), covers[0], new Date(2017, 1, 1), new Date(2017, 1, 1), new Date(2017, 1, 1)));
//        booksList.add(new Book(7, "The Scorch Trials", "James Dashner", "Mystery", "Dashner Productions", new Date(2017, 1, 1), covers[1], new Date(2017, 1, 1), new Date(2017, 1, 1), new Date(2017, 1, 1)));
//        booksList.add(new Book(8, "The Death Cure", "James Dashner", "Mystery", "Dashner Productions", new Date(2017, 1, 1), covers[2], new Date(2017, 1, 1), new Date(2017, 1, 1), new Date(2017, 1, 1)));
//
//        adapter.notifyDataSetChanged();
//    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
