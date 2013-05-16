package org.onlinetaskforce.web.frontend.panels.contact;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;
import org.wicketstuff.gmap.GMap;
import org.wicketstuff.gmap.api.GEvent;
import org.wicketstuff.gmap.api.GEventHandler;
import org.wicketstuff.gmap.api.GInfoWindow;
import org.wicketstuff.gmap.api.GLatLng;
import org.wicketstuff.gmap.api.GMarker;
import org.wicketstuff.gmap.api.GMarkerOptions;
import org.wicketstuff.gmap.api.GOverlay;

import java.util.List;

/**
 * Reusable basic ContactMapPageContentPanel
 * @author jordens
 * @since 8/03/13
 */
public class ContactMapPageContentPanel extends BasicPanel {
    private GMap gMap;
    /**
     * Initializes the view
     *
     * @param id The id
     */
    public ContactMapPageContentPanel(String id) {
        super(id);
        final GLatLng latLng = new GLatLng(50.944233, 5.111389);
        gMap = new GMap("map");
        gMap.setScrollWheelZoomEnabled(true);
        gMap.setCenter(latLng);
        gMap.setStreetViewControlEnabled(true);
        gMap.setZoom(15);
        GMarkerOptions gMarkerOptions = new GMarkerOptions(gMap, latLng);
        GMarker gMarker = new GMarker(gMarkerOptions);
        final GInfoWindow gInfoWindow = new GInfoWindow(latLng, "Ons Adres:<strong><br>Ansembroekstraat 13<br>3545 Halen<br>0032(0)477978427</strong>");
        gMarker.addListener(GEvent.mouseover, new GEventHandler() {
            @Override
            public void onEvent(AjaxRequestTarget target) {
                //target.appendJavaScript("alert('OnsAdres')");
                if (!adresOverlayExists(gMap, gInfoWindow)) {
                    gMap.addOverlay(gInfoWindow);
                }
            }
        });
        gInfoWindow.addListener(GEvent.closeclick, new GEventHandler() {
            @Override
            public void onEvent(AjaxRequestTarget target) {
                //target.appendJavaScript("alert('OnsAdres')");
                gMap.removeOverlay(gInfoWindow);
            }
        });
        gMap.addOverlay(gMarker);
        add(gMap);
    }

    private boolean adresOverlayExists(GMap gMap, GInfoWindow gInfoWindow) {
        List<GOverlay> overlays = gMap.getOverlays();
        for (GOverlay overlay : overlays) {
            if (overlay.equals(gInfoWindow)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    public GMap getgMap() {
        return gMap;
    }

    public void setgMap(GMap gMap) {
        this.gMap = gMap;
    }
}

