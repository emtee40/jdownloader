package jd.plugins.event;

import java.awt.AWTEvent;

import jd.plugins.Plugin;

/**
 * Mit diesen Events kommunizieren die Plugins mit dem Hauptprogramm
 * 
 * @author astaldo
 */
public class PluginEvent extends AWTEvent {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID             = -7243557276278230057L;
    /**
     * Neue Bytes wurden geladen bytes anzahl als parameter
     */
    public static final int   PLUGIN_DOWNLOAD_BYTES        = 0;
    /**
     * Maximaler Wert für die Fortschrittsanzeige
     */
    public static final int   PLUGIN_PROGRESS_MAX          = 1;
    /**
     * Der Wert der Fortschrittsanzeige soll um eins erhöht werden
     */
    public static final int   PLUGIN_PROGRESS_INCREASE     = 2;
    /**
     * Der Wert der Fortschrittsanzeige soll auf diesen Wert gesetzt werden
     */
    public static final int   PLUGIN_PROGRESS_VALUE        = 3;
    /**
     * Die Vorgang ist beendet
     */
    public static final int   PLUGIN_PROGRESS_FINISH       = 4;
    /**
     * Links wurden entschlüsselt
     */
    public static final int   PLUGIN_CRYPT_LINKS_DECRYPTED = 5;
    /**
     * Daten des Plugins haben sich geändert
     */
    public static final int   PLUGIN_DATA_CHANGED          = 6;
    /**
     * Download Geschwindigkeit hat sich geändert
     */
    public static final int   PLUGIN_DOWNLOAD_SPEED        = 7;
    /**
     * Die UI soll angezeigt werden
     */
    public static final int   PLUGIN_CONTROL_SHOW_UI       = 8;
    /**
     * Die Konfiguration soll angezeigt werden
     */
    public static final int   PLUGIN_CONTROL_SHOW_CONFIG   = 9;
    /**
     * Drag & Drop soll aktiviert werden
     */
    public static final int   PLUGIN_CONTROL_DND           = 10;
    /**
     * JDownloader soll beendet werden
     */
    public static final int   PLUGIN_CONTROL_EXIT          = 11;
    /**
     * Die Downloads sollen gestartet/gestoppt werden
     */
    public static final int   PLUGIN_CONTROL_START_STOP    = 12;
    /**
     * Die Verbindung sol getrennt werden
     */
    public static final int   PLUGIN_CONTROL_RECONNECT     = 13;
    /**
     * Plugin, von dem dieses Event ausgegangen ist
     */
    private Plugin            source;
    /**
     * ID des Events
     */
    private int               eventID;
    /**
     * Optionaler Parameter
     */
    private Object            parameter;
    /**
     * Erstellt ein neues PluginEvent
     * 
     * @param source Das Plugin, daß dieses Event ausgelöst hat
     * @param eventID Die ID des Events
     * @param parameter Ein optionaler Parameter
     */
    public PluginEvent(Plugin source, int eventID, Object parameter) {
        super(source, eventID);
        this.source = source;
        this.eventID = eventID;
        this.parameter = parameter;
    }
    
    public int getEventID() {
        return eventID;
    }
    public Plugin getSource() {
        return source;
    }
    // Hat das einen grund warumd as getParameter1 heißt?
    public Object getParameter1() {
        return parameter;
    }
}
