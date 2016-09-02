package org.telegram;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief Custom build vars FILL EVERYTHING CORRECTLY
 * @date 20 of June of 2015
 */
public class BuildVars {
    public static final Boolean debug = true;
    public static final Boolean useWebHook = false;
    public static final int PORT = 8443;
    public static final String EXTERNALWEBHOOKURL = "your-external-url:" + PORT;
    public static final String INTERNALWEBHOOKURL = "your-internal-url:" + PORT;
    public static final String pathToCertificatePublicKey = "/Users/wilsontan/Documents/selfsigned.pem";
    public static final String certificatePublicKeyFileName = "selfsigned.pem";

    public static final String OPENWEATHERAPIKEY = "99536655:AAG_uycvQeXgm-1Cdpk-zvBRUu2w_nz5p1Y";

    public static final String DirectionsApiKey = "99536655:AAG_uycvQeXgm-1Cdpk-zvBRUu2w_nz5p1Y";

    public static final String TRANSIFEXUSER = "<transifex-user>";
    public static final String TRANSIFEXPASSWORD = "<transifex-password>";

    public static final String pathToLogs = "./";

    public static final String linkDB = "jdbc:mysql://localhost:3306/YOURDATABSENAME?useUnicode=true&characterEncoding=UTF-8";
    public static final String controllerDB = "com.mysql.jdbc.Driver";
    public static final String userDB = "<your-database-user>";
    public static final String password = "<your-databas-user-password>";
}
