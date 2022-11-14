package it.ictgroup.service;

@SuppressWarnings("unused")
public class AppConstants {
    public static final int MAX_SIZE = 10000;
    public final static String RAW = "raw";
    public final static String RAW_POSTFIX = ".raw";
    public final static String VERSION = "version";
    public final static String MODEL = "model";
    public final static String CODE = "code";
    public final static String CALENDAR = "calendar";
    public final static String DESCRIPTION = "description";
    public final static String COMMISSION = "commission";
    public final static String CUSTOMER = "customer";
    public final static String ODL = "record";
    public final static String DATAIN = "dataIn";
    public final static String CREATION_DATE = "creationDate";
    public final static String DATE = "date";
    public final static String DEF = "def";
    public final static String DATAOUT = "dataOut";
    public final static String USERDELETE = "userDelete";
    public final static String FIRST = "_first";
    public final static String LAST = "_last";
    public final static String ISSUE = "issue";
    public final static String MODEL_CODE = "model.code";
    public final static String MODEL_TYPE = "model.type";
    public final static String MODEL_TYPEID = "model.type_id";
    public final static String MODEL_RECORDID = "model.record_id";
    public final static String MODEL_SERVICEID = "model.service_id";
    public final static String MODEL_USERNAME = "model.username";
    public final static String SCHEMA_CODE = "schemaCode";
    public final static String PARENTS_TREE_CODE = "parents.treeCode";
    public final static String PARENTS  = "parents";
    public final static String TREE_CODE = "treeCode";
    public final static String TREE_LEVEL = "treeLevel";
    public final static String TREE_UUID = "UUID";
    public final static String TREE_TYPE = "type";
    public final static String TREE_TOTAL_LEVELS = "treeTotalLevels";
    public final static String PARENTS_TREE_LEVEL = "parents.treeLevel";
    public final static String PARENTS_TREE_TOTAL_LEVELS = "parents.treeTotalLevels";
    public final static String PARENTS_TYPE = "parents.type";
    public final static String PARENTS_UUID = "parents.UUID";
    public final static String RECORD_ID = "record_id";
    public final static String RECORD = "record";
    public final static String SCHEMA ="schema";
    public final static String STRUCTURE ="structure";
    public final static String TYPE = "type";
    public final static String TREE = "tree";
    public final static String USER = "user";
    public final static String NODES = "nodes";

    public final static String UUID = "UUID";
    public static final String FIRST_SCROLL = "first_scroll";

    // ODL
    public static final String ASSIGNEE_HEADMAN_COMMISSION = "references_assignee.headmanCommission_id";
    public static final String ASSIGNEE_SUPPLIER = "references_assignee.supplier_id";
    public static final String ASSIGNEE_COMPANY = "references_assignee.company_id";
    public static final String ASSIGNEE_SUPERVISOR_INTERNAL = "references_assignee.supervisorInternal_id";
    public static final String ASSIGNEE_SUPERVISOR_EXT = "references_assignee.supervisorExternal_id";
    public static final String ASSIGNEE_TECHNICIAN_COMMISSION = "references_assignee.technicianCommission_id";
    public static final String ASSIGNEE_MAINTAINER = "references_assignee.maintainer_id";
    public static final String WEBHOOK_URL = "webhook_url";
    public static final String STATUS = "status";
    public static final String STATUS_OPEN = "OPEN";
    public static final String STATUS_INSERVICE = "IN_SERVICE";
    public static final String STATUS_CLOSED = "CLOSED";
    public static final String SOURCE_CPS = "app:cps_odl_actions";
    public static final String SOURCE_APP = "app";
    public static final String GENERATEDBY_PLAN = "plan";
    public static final String GENERATEDBY_ISSUE = "issue";
    public static final String GENERATEDBY = "generated_by";
    public static final String DATE_REQUESTED = "date_requested";
    public static final String DATE_INSERTION = "date_insertion";
    public static final String DATE_HANDLING = "date_handling";
    public static final String DATE_CLOSE = "date_close";

    // attachments type
    public final static String ATTACHMENT = "attachment";
    public final static String MODEL_EXTERNALUUID = "model.external_uuid";
    public final static String MODEL_EXTERNALTYPE = "model.external_type";

    // actions
    public final static String ACTION = "action";
    public final static String PERFORMEDACTION = "performedaction";
    public final static String ACTION_UUID = "action_uuid";
    public final static String EXTERNAL_UUID = "external_uuid";
    public final static String EXTERNAL_TYPE = "external_type";
    public final static String PLAN_UUID = "plan_uuid";
    public final static String WORKINGMODE_FIELD = "workingmode_id";
    public final static String SERVICE_FIELD = "service_id";
    public final static String ODL_PRIORITY_FIELD = "odl_priority_id";

    // plan
    public final static String TASK_ID = "task_id";
    public final static String TASK_ACTIVITIES = "task_activities";

    // QRCODE_KEYS
    public final static String QRKEY_COMMISSION = "commission";
    public final static String QRKEY_UUID = "UUID";
    public final static String QRKEY_ASSETUUID = "assetuuid";
    public final static String QRKEY_ASSETTYPE = "assettype";
    public final static String QRKEY_ASSETCODE = "assetcode";
    public final static String QRKEY_SERVICEUUID = "serviceuuid";
    public final static String QRKEY_SERVICECODE = "servicecode";
    public final static String QRKEY_PRIORITYUUID = "priorityuuid";
    public final static String QRKEY_PRIORITYCODE = "prioritycode";
    public final static String QRKEY_PLANUUID = "planuuid";
    public final static String QRKEY_PLANCODE = "plancode";

    // COMMISSION
    public static final String FLOTTE_COMMISSION_CODE = "1900028";

    // TYPES
    public static final String ATTACHEDDOCUMENT_TYPE = "attacheddocument";
    public static final String FLOTTEASSEGNAZIONE_TYPE = "flotteassegnazione";
    public static final String FLOTTEGESTIONEDOTAZIONI_TYPE = "flottegestionedotazioni";
    public static final String FLOTTEATTREZZATURE_TYPE = "flotteattrezzature";
    public static final String FLOTTEMEZZI_TYPE = "flottemezzi";
    public static final String FLOTTEMULTIFUNZIONI_TYPE = "flottemultifunzioni";
    public static final String FLOTTETELEFONIA_TYPE = "flottetelefonia";
    public static final String FLOTTEDOTAZIONI_TYPE = "flottedotazioni";
    public static final String GESTIONETELEFONIA_TYPE = "gestionetelefonia";
    public static final String FLOTTEFORNITUREIT_TYPE = "flottefornitureit";
    public static final String GESTIONEMEZZI_TYPE = "gestionemezzi";
    public static final String SERVICE_TYPE = "servizio";
    public static final String WORKINGMODE_TYPE = "actionworkingmode";
    public final static String SURVEY_TYPE = "survey";
    public final static String SURVEY_RESPONSE_TYPE = "response";

    // ASSETS SPECIAL KEYS
    public final static String ASSET_SPECIAL_KEYS = "schemaCode,UUID,commission,customer";

    public static final String DATA_FINE = "data_fine";
    public static final String ATTREZZATURE_FIELD = "attrezzatura_id";
    public static final String MEZZO_FIELD = "flottemezzo_id";
    public static final String MULTIFUNZIONI_FIELD = "multifunzioni_id";
    public static final String TELEFONIA_FIELD = "flottetelefonia_id";
    public static final String DOTAZIONE_FIELD = "dotazione_id";
    public static final String FORNITURAIT_FIELD = "fornitura_id";
    public static final String ATTACHEDDOCUMENT_RECORD_ID = "record_id";
    public static final String ATTACHEDDOCUMENT_TIPOLOGIADOCUMENTO_ID = "tipologia_id";
    public static final String ODL_RECORD_ID_FIELD = "record_id";

    public static final String MEZZIDISMISSIONE_FIELD = "dismissione";
    public static final String MEZZIDATADISMISSIONE_FIELD = "datadismissione";
    public static final String MEZZIUBICAZIONE_FIELD = "ubicazione_id";
    public static final String ATTREZZATUREDISMISSIONE_FIELD = "dismissione_id";
    public static final String ATTREZZATUREDATADISMISSIONE_FIELD = "datadidismissione";
    public static final String ATTREZZATUREUBICAZIONE_FIELD = "ubicazione_id";
    public static final String DOTAZIONEDISMISSIONE_FIELD = "dismissione_id";
    public static final String DOTAZIONEDATADISMISSIONE_FIELD = "datadismissione";
    public static final String TELEFONIADATADISATTIVAZIONE_FIELD = "datadisattivazione";

    public static final String AND = "&";
}
