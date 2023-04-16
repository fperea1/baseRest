package com.base.rest.constant;

public final class Constantes {

	private Constantes() {}
	
    // mapeo peticiones controller
	
	public static final String AUTENTICATION = "autenticacion";
	
	public static final String GENERAR_TOKEN = "generate-token";
	
	public static final String LOGOUT = "logout";
	
	public static final String CONTACTO = "contacto";
	
	public static final String CONFIG = "/configuracion";
	
	public static final String USUARIOS = "/usuarios";
	
	public static final String ROLES = "/roles";
	
	public static final String LOGS = "/logs";
	
	public static final String FIND_ALL = "/findAll";
	
	public static final String FIND = "/find";
	
	public static final String FIND_BY_FILTER = "/findByFilter";
	
	public static final String SAVE = "/save";
	
	public static final String UPDATE = "/update";
	
	public static final String DELETE = "/delete";
	
	public static final String DEACTIVATE = "/deactivate";
	
	public static final String ACTIVATE = "/activate";
	
	public static final String CAMBIO_PASSWORD = "/cambioPassword";
	
	public static final String CAMBIO_PASSWORD_ADMIN = "/cambioPasswordAdmin";
	
	public static final String ENVIO_CONSULTA = "envioConsulta";
	
	public static final String GET_REPORT_EXCEL = "/getReportExcel";
	
	public static final String FIND_FOR_SELECT = "/findForSelect";
	
	// Funcionalidades
	
	public static final String CONFIGURACION = "configuracion";
	
	public static final String USUARIO = "usuario";
	
	// Acciones Log
	
	public static final String LOGIN = "login";
	
	public static final String LOGOUT_LOG = "logout";
	
	public static final String ALTA = "alta";
	
	public static final String EDICION = "edicion";
	
	public static final String BAJA = "baja";
	
	public static final String ACTIVAR = "activar";
	
	public static final String DESACTIVAR = "desactivar";
	
	public static final String CAMBIO_PASS_USUARIO = "cambio.pass.usuario";
	
	public static final String CAMBIO_PASS_ADMIN = "cambio.pass.admin";
	
	// Resultados operacion
	
	public static final String OPERACION_CORRECTA = "operacion.correcta";
	
	// Mensajes excepciones
	
	public static final String EXC_INTEGRIDAD_DATOS = "infraccion.integridad.datos";
	
	public static final String EXC_FALTA_PARAM_HEADER = "falta.param.header";
	
	public static final String EXC_METODO_NO_SOPORTADO = "metodo.no.soportado";
	
	public static final String EXC_AUTH_ERRONEA = "autenticacion.error";
	
	public static final String EXC_FALTAN_PARAM_PETICION = "faltan.parametros.peticion";
	
	public static final String EXC_LIMITE_CARACTERES_PASSWORD = "password.size";
	
	public static final String EXC_PASSWORDS_DIFERENTES = "passwords.diferentes";
	
	public static final String EXC_PASSWORD_ANT_ERRONEA = "password.ant.error";
	
	public static final String EXC_NO_EXISTE_ENTIDAD = "no.existe.entidad";
	
	public static final String EXC_CREDENCIALES_ERRONEAS = "credenciales.error";
	
	public static final String EXC_JSON_PARSE = "error.mapeo.json";
	
	public static final String EXC_REPORT_POI = "error.report.xlsx";
	
	public static final String NO_AUTH = "no.autorizado";
	
	public static final String ERROR_RECUPERAR_NOMBRE_USUARIO_TOKEN = "error.recuperar.nombre.usuario.token";
	
	public static final String TOKEN_CADUCADO = "token.caducado";
	
	public static final String ERROR_USERNAME_PASSWORD = "error.username.password";
	
	public static final String EXC_PASSWORD_ANTERIOR_OBLIGATORIO = "error.password.anterior.obligatorio";
	
	// Símbolos
	
	public static final String SEPARADOR_DOS_PUNTOS = ": ";
	
	// EXCEL
	
	public static final int MAX_ROWS_XLSX = 1048576;
	
	public static final String NOMBRE = "nombre";
	
	public static final String USERNAME = "username";
	
	public static final String EMAIL = "email";
	
	public static final String FECHA_ALTA = "fecha.alta";
	
	public static final String FECHA_BAJA = "fecha.baja";
	
	public static final String ACTIVO = "activo";
	
	public static final String ENTIDAD = "entidad";
	
	public static final String ACCION = "accion";
	
	public static final String OBSERVACIONES = "observaciones";
	
	public static final String FECHA = "fecha";
	
	public static final String VALOR = "valor";
	
	public static final String SI = "si";
	
	public static final String NO = "no";
	
	public static final String T_ROLES = "roles";
	
	// MATCH MODE PARA BUSQUEDAS
	
	public static final String STARTS_WITH = "startsWith";
	
	public static final String ENDS_WITH = "endsWith";
	
	public static final String CONTAINS = "contains";
	
	public static final String NOT_CONTAINS = "notContains";
	
	public static final String EQUALS = "equals";
	
	public static final String NOT_EQUALS = "notEquals";
	
	public static final String DATE_IS = "dateIs";
	
	public static final String DATE_IS_NOT = "dateIsNot";
			
	public static final String DATE_BEFORE = "dateBefore";
			
	public static final String DATE_AFTER = "dateAfter";
	
	public static final String IN = "in";
	
	public static final String SELECT = "select";
	
	public static final String BOOLEAN = "boolean";
	
	// FORMATOS FECHAS
	
	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	
	public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	
	public static final String YYYY_MM_DD_T_HH_MM_SS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
	// NOMBRE PESTAÑAS EXCEL
	
	public static final String SHEET_LOGS = "logs";
	
	public static final String SHEET_CONFIGURACION = "configuracion";
	
	public static final String SHEET_USUARIOS = "usuarios";
	
	// CONTENT TYPE
	
	public static final String CONTENT_EXCEL = "application/vnd.ms-excel";
	
	// ATTACHMENTS
	
	public static final String ATTACHMENTS_EXCEL = "attachment; filename=report.xlsx";
	
	// VALIDACIONES
	
	public static final String VALIDATION_NOMBRE_CONFIG_SIZE = "validation.nombre.config.size";
	
	public static final String VALIDATION_VALOR_CONFIG_SIZE = "validation.valor.config.size";
	
	public static final String VALIDATION_USERNAME_LOG_SIZE = "validation.username.log.size";
	
	public static final String VALIDATION_ENTIDAD_LOG_SIZE = "validation.entidad.log.size";
	
	public static final String VALIDATION_ACCION_LOG_SIZE = "validation.accion.log.size";
	
	public static final String VALIDATION_OBSERVACIONES_LOG_SIZE = "validation.observaciones.log.size";
	
	public static final String VALIDATION_NOMBRE_USUARIO_SIZE = "validation.nombre.usuario.size";
	
	public static final String VALIDATION_USERNAME_USUARIO_SIZE = "validation.username.usuario.size";
	
	public static final String VALIDATION_PASSWORD_USUARIO_SIZE = "validation.password.usuario.size";
	
	
	public static final String VALIDATION_EMAIL_USUARIO_FORMATO = "validation.email.usuario.formato";
	
	
	public static final String VALIDATION_NOMBRE_OBLIGATORIO = "validation.nombre.obligatorio";
	
	public static final String VALIDATION_VALOR_OBLIGATORIO = "validation.valor.obligatorio";
	
	public static final String VALIDATION_USERNAME_OBLIGATORIO = "validation.username.obligatorio";
	
	public static final String VALIDATION_ENTIDAD_OBLIGATORIO = "validation.entidad.obligatorio";
	
	public static final String VALIDATION_ACCION_OBLIGATORIO = "validation.accion.obligatorio";
	
	public static final String VALIDATION_OBSERVACIONES_OBLIGATORIO = "validation.observaciones.obligatorio";
	
	public static final String VALIDATION_FECHA_OBLIGATORIO = "validation.fecha.obligatorio";
	
	public static final String VALIDATION_PASSWORD_OBLIGATORIO = "validation.password.obligatorio";
	
	public static final String VALIDATION_EMAIL_OBLIGATORIO = "validation.email.obligatorio";
	
	public static final String VALIDATION_FECHA_ALTA_OBLIGATORIO = "validation.fecha.alta.obligatorio";
	
	public static final String VALIDATION_ACTIVO_OBLIGATORIO = "validation.activo.obligatorio";
	
	public static final String VALIDATION_ASUNTO_OBLIGATORIO = "validation.asunto.obligatorio";
	
	public static final String VALIDATION_CONSULTA_OBLIGATORIO = "validation.consulta.obligatorio";
	
	public static final String VALIDATION_NUEVA_PASSWORD_OBLIGATORIO = "validation.nueva.password.obligatorio";
	
	public static final String VALIDATION_CONFIRMACION_PASSWORD_OBLIGATORIO = "validation.confirmacion.password.obligatorio";
			
}
