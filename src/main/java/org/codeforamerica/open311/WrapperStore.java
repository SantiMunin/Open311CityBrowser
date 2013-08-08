package org.codeforamerica.open311;

import org.codeforamerica.open311.facade.APIWrapper;

public class WrapperStore {

    private static WrapperStore instance = new WrapperStore();
    private APIWrapper wrapper;

    private WrapperStore() {
    }

    public static WrapperStore getInstance() {
        return instance;
    }

    public void storeWrapper(APIWrapper wrapper) {
        this.wrapper = wrapper;
    }

    public APIWrapper getWrapper() {
        return wrapper;
    }


}
