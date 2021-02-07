package com.new_name.treasure_hunt_app.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Geo_details.class}, version = 1, exportSchema = false)
public abstract class Geo_detailsRoomDatabase extends RoomDatabase {

    public abstract Geo_detailsDao Geo_detailsDao();
    private static Geo_detailsRoomDatabase INSTANCE;

    static Geo_detailsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Geo_detailsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Geo_detailsRoomDatabase.class, "Geo_details_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(tryRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static RoomDatabase.Callback tryRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final Geo_detailsDao mDao;


        PopulateDbAsync(Geo_detailsRoomDatabase db) {
            mDao = db.Geo_detailsDao();
        }



        Geo_details[] GeoArray={
                new Geo_details("g1",  "30.007007", "31.4559342","Mac_Nargs_mobile",
                        "I’m tall when I’m young, and I’m short when I’m old. What am I?","candle",false,5),

                new Geo_details("g2",  "30.0207138", "31.4958104","Point_90",
                        "Where does today come before yesterday?","dictionary",false,7),

                new Geo_details("g3",  "30.0422266", "31.4751421","Waterway_1",
                        "What invention lets you look right through a wall?","window",false,4),

                new Geo_details("g4",  "30.0184527", "31.4210510","Ibn_Elsham",
                        "What has one eye, but can’t see?","needle",false,6),

                new Geo_details("g5",  "30.0199213", "31.4107171","Near_downtown",
                        "What has legs, but doesn’t walk?","table",false,10)
        };



        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            //   mDao.deleteAll();

            for (int i = 0; i <= GeoArray.length - 1; i++) {

                Geo_details geo = GeoArray[i];
                mDao.insert(geo);
            }
            return null;
        }
    }



}
