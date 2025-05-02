module com.example.mohapi_parollmanagement_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires itextpdf;
    requires jdk.compiler;
    requires jbcrypt; // ✅ Give access to iText

    opens com.example.mohapi_parollmanagement_system to javafx.fxml;
    exports com.example.mohapi_parollmanagement_system;
    exports com.example.mohapipayroll_management_system;
    opens com.example.mohapipayroll_management_system to javafx.fxml;
}
