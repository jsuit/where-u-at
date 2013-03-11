package gitmad.app.WhereUAt;

import java.io.IOException;
import java.util.List;

import android.app.Fragment;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ResultsFragment extends Fragment implements OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container,
                false);
        setupFragment(view);
        return view;
    }

    private void setupFragment(View view) {
        TextView resultView = (TextView) view
                .findViewById(R.id.result_value_label);

        View returnButton = view.findViewById(R.id.back_to_menu_button);
        returnButton.setOnClickListener(this);

        final Geocoder geocoder = new Geocoder(this.getActivity());

        Intent i = getActivity().getIntent();
        String location = i.getStringExtra(MainActivity.EXTRA_MESSAGE);
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String message = "";
                if (address.getLocality() != null) {
                    message = String.format("%s", address.getLocality());
                } else {
                    message = "Unable to locate";
                }
                resultView.setText(message);
            } else {
                resultView.setText("Unable to locate");
            }
        } catch (IOException e) {
            Log.d("Demo", "IOException");
        }
    }
    
    @Override
    public void onClick(View arg0) {
        getActivity().finish();      
    }
}
