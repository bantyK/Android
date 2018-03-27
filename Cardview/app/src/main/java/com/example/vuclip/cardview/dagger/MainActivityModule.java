package com.example.vuclip.cardview.dagger;

import com.example.vuclip.cardview.adapter.CustomAdapter;
import com.example.vuclip.cardview.model.DataModel;
import com.example.vuclip.cardview.model.MyData;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Banty on 27/03/18.
 */

@Module
public class MainActivityModule {
    @Provides
    public List<DataModel> provideData() {
        List<DataModel> data = new ArrayList<>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i]
            ));
        }

        return data;
    }

    @Provides
    public CustomAdapter provideCustomAdapter(List<DataModel> data) {
        return new CustomAdapter(data);

    }
}
