package com.dollop.exam101.Basics.vimeoplayer.network;


import com.dollop.exam101.Basics.vimeoplayer.model.vimeo.VimeoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VimeoInterface {
    @GET("{video_id}/config")
    Call<VimeoResponse> getVimeoUrlResponse(@Path("video_id") String id);

}
