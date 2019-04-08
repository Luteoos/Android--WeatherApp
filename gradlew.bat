package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_anno_models_realmmodels_UserRealmRealmProxy extends com.anno.models.realmmodels.UserRealm
    implements RealmObjectProxy, com_anno_models_realmmodels_UserRealmRealmProxyInterface {

    static final class UserRealmColumnInfo extends ColumnInfo {
        long uuidIndex;
        long changedIndex;
        long createdIndex;
        long authorIndex;
        long syncIndex;
        long firstNameIndex;
        long lastNameIndex;
        long emailIndex;
        long userNameIndex;
        long occupationIndex;
        long isVerifiedIndex;
        long passwordIndex;
        long userStatusIndex;
        long notificationSettingsIndex;
        long photoIndex;
        long teamsIndex;
        long toDosIndex;
        long isPhotoSetFilteredIndex;
        long notificationsIndex;

        UserRealmColumnInfo(OsSchemaInfo schemaInfo) {
            super(19);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("UserRealm");
            this.uuidIndex = addColumnDetails("uuid", "uuid", objectSchemaInfo);
            this.changedIndex = addColumnDetails("changed", "changed", objectSchemaInfo);
            this.createdIndex = addColumnDetails("created", "created", objectSchemaInfo);
            this.authorIndex = addColumnDetails("author", "author"