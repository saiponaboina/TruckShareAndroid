package com.ritul.truckshare.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ritul.truckshare.R;
import com.ritul.truckshare.Server.RSMServer;
import com.ritul.truckshare.Util.GPSTracker;

/**
 * Created by Keval on 2/25/2016.
 */
public class MapFragment extends Fragment implements LocationListener{
    GPSTracker gps;
    MapView mapView;
    GoogleMap map;
    Location location;
    TextView locationTv;
    private RelativeLayout linViewDownload;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_layout, container, false);

        locationTv = (TextView) v.findViewById(R.id.txtlocation);
        linViewDownload = (RelativeLayout) v.findViewById(R.id.relMain);

        gps = new GPSTracker(getActivity());

            if (gps.canGetLocation()){
                // Gets the MapView from the XML layout and creates it
                mapView = (MapView) v.findViewById(R.id.mapview);
                mapView.onCreate(savedInstanceState);

                // Gets to GoogleMap from the MapView and does initialization stuff
                map = mapView.getMap();
                map.getUiSettings().setMyLocationButtonEnabled(false);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, RSMServer.EXTERNAL_WRITE_SD_CODE);
                }
                map.setMyLocationEnabled(true);

                // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
                MapsInitializer.initialize(this.getActivity());

                // Updates the location and zoom of the MapView
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
                map.animateCamera(cameraUpdate);


                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String bestProvider = locationManager.getBestProvider(criteria, true);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Snackbar.make(linViewDownload, "Storage permission is needed to access shared external storage.",
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction("ok", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, RSMServer.EXTERNAL_WRITE_SD_CODE);
                                    }
                                })
                                .show();
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, RSMServer.EXTERNAL_WRITE_SD_CODE);
                    }
                }
                location = locationManager.getLastKnownLocation(bestProvider);
                //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.addMarker(new MarkerOptions().position(latLng));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.animateCamera(CameraUpdateFactory.zoomTo(15));
                locationTv.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
            }
            else {
                gps.showSettingsAlert();
            }
        return v;
    }



    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        map.addMarker(new MarkerOptions().position(latLng));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
        locationTv.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
