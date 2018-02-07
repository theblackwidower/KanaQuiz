package com.noprestige.kanaquiz.logs;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {LogDailyRecord.class, LogKanaRecord.class, LogIncorrectAnswer.class}, version = 1)
@TypeConverters({LogTypeConversion.class})
public abstract class LogDatabase extends RoomDatabase
{
    public static LogDao DAO = null;
    public abstract LogDao logDao();
}
