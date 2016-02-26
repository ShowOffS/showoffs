package in.showoffs.showoffs.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.showoffs.showoffs.R;
import in.showoffs.showoffs.utils.Utility;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment {

    @Bind(R.id.status_text)
    EditText status;
    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @OnClick(R.id.post)
    public void postStatus(View view) {
        GraphRequest request = new GraphRequest(Utility.getAccessToken("144952458943617"), Profile.getCurrentProfile().getId() +"/feed");
        Bundle parameters = new Bundle();
        parameters.putString("limit","1");
        parameters.putString("message", status.getText().toString());
        request.setHttpMethod(HttpMethod.POST);
        request.setParameters(parameters);
        request.setCallback(new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                Snackbar.make(getView(), response.toString(), Snackbar.LENGTH_INDEFINITE).show();
            }
        });
        request.executeAsync();
    }
}
