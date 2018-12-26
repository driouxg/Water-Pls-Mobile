package com.dryox.water_pls_mobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dryox.water_pls_mobile.domain.value.DonatorVO;
import com.dryox.water_pls_mobile.domain.value.GeoCoordinateVO;
import com.dryox.water_pls_mobile.domain.value.GeographicLocationVO;
import com.dryox.water_pls_mobile.domain.value.NameVO;
import com.dryox.water_pls_mobile.domain.value.PasswordVO;
import com.dryox.water_pls_mobile.domain.value.UsernameVO;
import com.dryox.water_pls_mobile.service.asynctask.ConnectDonatorToServer;
import com.dryox.water_pls_mobile.service.asynctask.ConnectRequesterToServer;
import com.dryox.water_pls_mobile.service.asynctask.DonatorSearchRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button donateButton = findViewById(R.id.donateButton);
        Button requestButton = findViewById(R.id.requestButton);
        Button searchButton = findViewById(R.id.searchButton);

        configureDonateButton(donateButton);
        configureRequestButton(requestButton);
        configureSearchButton(searchButton);
    }


    private void configureRequestButton(Button requestButton) {
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRequestButtonClick();
            }
        });
    }

    private void configureSearchButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void configureDonateButton(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDonateButtonClick();
            }
        });
    }

    private void onDonateButtonClick() {
        ConnectDonatorToServer connectDonatorToServer = new ConnectDonatorToServer(buildDonator());
        connectDonatorToServer.execute();
    }

    private void onSearchButtonClick() {
        DonatorSearchRequest donatorSearchRequest = new DonatorSearchRequest();
        donatorSearchRequest.execute();
    }

    private void onRequestButtonClick() {
        ConnectRequesterToServer connectRequesterToServer = new ConnectRequesterToServer();
        connectRequesterToServer.execute();
    }

    private DonatorVO buildDonator() {
        UsernameVO username = new UsernameVO("username");
        PasswordVO password = new PasswordVO("password");
        NameVO firstName = new NameVO("Hingle");
        NameVO lastName = new NameVO("McCringleBerry");
        GeoCoordinateVO latitude = new GeoCoordinateVO(1.53f);
        GeoCoordinateVO longitude = new GeoCoordinateVO(1.2234f);
        GeographicLocationVO geographicLocation = new GeographicLocationVO(latitude, longitude);
        return new DonatorVO(username, password, firstName, lastName, geographicLocation);
    }
}
