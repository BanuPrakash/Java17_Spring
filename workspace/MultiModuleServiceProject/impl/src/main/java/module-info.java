import com.adobe.api.LogService;
import com.adobe.impl.LogServiceStdOut;

module impl {
    requires api;
    exports  com.adobe.impl;
    provides LogService with LogServiceStdOut; // required for ServiceLoader
}