package com.new_name.treasure_hunt_app.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.ExecutionException;

public class Geo_detailsRepository {


    private Geo_detailsDao mgeo_detailsDao;
    private Geo_details mgeo_details;

    public Geo_detailsRepository(Application application){

        Geo_detailsRoomDatabase db=Geo_detailsRoomDatabase.getDatabase(application);
        mgeo_detailsDao=db.Geo_detailsDao();
        //  mgeo_details=mgeo_detailsDao.getGeo_details(geoId);
    }

    public Geo_details getGeo_details(String geoId){
        Log.d("loco", "  REPOOO     getGeo_details: ");
        // mgeo_details=mgeo_detailsDao.getGeo_details(geoId);
        try {
            mgeo_details= new getAsyncTask(mgeo_detailsDao).execute(geoId).get();
        } catch (ExecutionException e) {
            Log.d("loco", "  REPOOO 1    getGeo_details: ");
            //e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d("loco", "  REPOOO 2    getGeo_details: ");
            //  e.printStackTrace();
        }


        return mgeo_details;
    }

    public void insert (Geo_details geo_details) {
        new insertAsyncTask(mgeo_detailsDao).execute(geo_details);
    }


    private static class insertAsyncTask extends AsyncTask<Geo_details, Void, Void> {

        private Geo_detailsDao mAsyncTaskDao;

        insertAsyncTask(Geo_detailsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Geo_details... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    private class getAsyncTask extends AsyncTask<String, Void, Geo_details> {

        private Geo_detailsDao mAsyncTaskDao;

        getAsyncTask(Geo_detailsDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Geo_details doInBackground(final String... params) {
            Log.d("loco", "  getAsyncTask     Geo_details: ");
            return mAsyncTaskDao.getGeo_details(params[0]);


        }
    }


}
