# Pruebas Unitarias y Cobertura de Microservicios

## Objetivo

PGVet incorpora pruebas unitarias por capas en seis microservicios Spring Boot. Cada capa se prueba de forma aislada: entidades sin base de datos, servicios con Mockito, controllers con MockMvc standalone y repositorios con `@DataJpaTest` sobre H2 en memoria. No se usa MySQL, Docker ni red externa en los tests.

## Microservicios Evaluados

- auth (`com.pgvet.auth`)
- usuario (`com.pgvet.usuario`)
- mascota (`com.pgvet.mascota`)
- cita (`com.pgvet.cita`)
- notificacion (`com.pgvet.notificacion`)
- ficha clinica (`com.pgvet.ficha`)

## Capas Probadas

| Capa | Técnica | Qué valida |
|------|---------|------------|
| **Modelo** | JUnit 5 puro | Constructor vacío, getters/setters y comportamiento por defecto de la entidad JPA |
| **Servicio** | `@ExtendWith(MockitoExtension.class)` | Lógica de negocio con repositorio y Feign simulados |
| **Controlador** | MockMvc `standaloneSetup` | Rutas HTTP, códigos de respuesta y JSON sin levantar la app completa |
| **Repositorio** | `@DataJpaTest` + H2 | Persistencia real en memoria: `save`, `findById`, `findAll` y consultas derivadas |

## Resumen de Cobertura

| Microservicio | Modelo | Servicio | Controlador | Repositorio | Total |
|--------------|--------|----------|-------------|-------------|-------|
| auth | 3 | 6 | 3 | 4 | **17** |
| usuario | 3 | 7 | 3 | 4 | **18** |
| mascota | 3 | 6 | 3 | 4 | **17** |
| cita | 3 | 7 | 3 | 4 | **18** |
| notificacion | 3 | 6 | 3 | 3 | **16** |
| ficha clinica | 3 | 6 | 3 | 4 | **17** |

**Total general: 103 tests** (incluye 1 test de aplicación por microservicio).

### Detalle por capa y microservicio

**auth**
- Modelo: `UsuarioAuthTest` — activo por defecto, getters/setters, cambio de rol
- Servicio: `UsuarioAuthServiceTest` — CRUD, listar, errores al actualizar/eliminar inexistente
- Controlador: `UsuarioAuthControllerTest` — GET 200, GET 404, POST 201
- Repositorio: `UsuarioAuthRepositoryTest` — save, findById, findAll, `existsByCorreo`

**usuario**
- Modelo: `UsuarioTest` — activo por defecto, getters/setters, especialidad veterinario
- Servicio: `UsuarioServiceTest` — RUT válido/inválido, CRUD, listar
- Controlador: `UsuarioControllerTest` — GET 200, GET 404, POST 201
- Repositorio: `UsuarioRepositoryTest` — save, findById, findAll, `findByRol`

**mascota**
- Modelo: `MascotaTest` — activo por defecto, getters/setters, microchip
- Servicio: `MascotaServiceTest` — tutor Feign válido/404, listar, eliminar inexistente
- Controlador: `MascotaControllerTest` — GET 200, GET 404, POST 201
- Repositorio: `MascotaRepositoryTest` — save, findById, findAll, `findByTutorId`

**cita**
- Modelo: `CitaTest` — estado AGENDADA por defecto, getters/setters, observación
- Servicio: `CitaServiceTest` — crear, duplicada, ID inválido, estado inválido, eliminar
- Controlador: `CitaControllerTest` — GET 200, GET 404, POST 201
- Repositorio: `CitaRepositoryTest` — save, findById, findAll, `findByEstado`

**notificacion**
- Modelo: `NotificacionTest` — constructor, getters/setters, marcar leída
- Servicio: `NotificacionServiceTest` — crear, usuario 404, listar, eliminar inexistente
- Controlador: `NotificacionControllerTest` — GET 200, GET 404, POST 201
- Repositorio: `NotificacionRepositoryTest` — save, findById, findAll (sin métodos custom en el repositorio)

**ficha clinica**
- Modelo: `FichaClinicaTest` — constructor, getters/setters, observaciones
- Servicio: `FichaClinicaServiceTest` — crear, mascota 404, ID inválido, listar por mascota
- Controlador: `FichaClinicaControllerTest` — GET 200, GET 404, POST 201
- Repositorio: `FichaClinicaRepositoryTest` — save, findById, findAll, `findByMascotaId`

## Dos Microservicios Para Presentar

### 1. usuario (recomendado)

**Por qué:** 18 tests, las cuatro capas completas, regla de negocio visible (validación de RUT) y controller con GET y POST.

**Archivo sugerido para captura de código:** `codigo-fuente/usuario-service/usuario-service/src/test/java/com/pgvet/usuario/service/UsuarioServiceTest.java`

**Comando para BUILD SUCCESS:**

```powershell
Set-Location codigo-fuente\usuario-service\usuario-service
.\mvnw.cmd test
```

### 2. cita (recomendado)

**Por qué:** 18 tests, cuatro capas, lógica rica (cita duplicada, estados, Feign simulado) y repositorio con consulta `findByEstado`.

**Archivo sugerido para captura de código:** `codigo-fuente/cita-service/src/test/java/com/pgvet/cita/repository/CitaRepositoryTest.java`

**Comando para BUILD SUCCESS:**

```powershell
Set-Location codigo-fuente\cita-service
.\mvnw.cmd test
```

**Alternativa:** auth también tiene 17 tests en cuatro capas; útil si se quiere mostrar el microservicio de credenciales.

## Reglas de Negocio Críticas (referencia)

- **auth:** credenciales activas al crear; DTO sin password; no existe login/JWT en el código
- **usuario:** RUT formato `12345678-9`; usuario activo al crear
- **mascota:** tutor debe existir vía Feign antes de guardar
- **cita:** no duplicar veterinario+fecha+hora; estados AGENDADA/CONFIRMADA/CANCELADA/REALIZADA
- **notificacion:** destinatario debe existir vía Feign
- **ficha clinica:** mascota, veterinario y cita deben existir vía Feign

## Deuda Técnica

- **Auth:** no hay login ni JWT; no se pueden probar esas reglas hasta implementarlas
- **Controllers:** no se probó PUT ni DELETE en MockMvc (los endpoints existen; los tests cubren GET y POST como mínimo exigido)
- **Feign 503:** faltan tests de servicio externo caído en todos los microservicios Feign
- **Integración:** faltan pruebas end-to-end con servicios levantados
- **notificacion / ficha clinica:** `@SpringBootTest` completo no es viable por incompatibilidad Spring Boot 4 + OpenFeign en classpath de test; se usa test de clases principales + pruebas por capa
- **Fuera de esta fase:** vacuna, receta, inventario, pago

## Cómo Ejecutar Las Pruebas

Desde la raíz del repositorio (Windows PowerShell):

```powershell
Set-Location codigo-fuente\auth-service\auth-service; .\mvnw.cmd test
Set-Location codigo-fuente\usuario-service\usuario-service; .\mvnw.cmd test
Set-Location codigo-fuente\mascota-service\mascota-service; .\mvnw.cmd test
Set-Location codigo-fuente\cita-service; .\mvnw.cmd test
Set-Location codigo-fuente\notificacion-service\notificacion-service; .\mvnw.cmd test
Set-Location codigo-fuente\ficha-clinica-service\ficha-clinica-service; .\mvnw.cmd test
```

Linux / macOS:

```bash
cd codigo-fuente/auth-service/auth-service && ./mvnw test
cd codigo-fuente/usuario-service/usuario-service && ./mvnw test
cd codigo-fuente/mascota-service/mascota-service && ./mvnw test
cd codigo-fuente/cita-service && ./mvnw test
cd codigo-fuente/notificacion-service/notificacion-service && ./mvnw test
cd codigo-fuente/ficha-clinica-service/ficha-clinica-service && ./mvnw test
```

### Configuración H2 (tests)

Cada microservicio usa `src/test/resources/application.properties` con datasource H2 y `ddl-auto=create-drop`. No modifica la configuración de producción en `src/main/resources`.

## Evidencia sugerida para el equipo

1. **Árbol de tests:** captura de `src/test/java` mostrando carpetas `model`, `service`, `controller`, `repository`
2. **Código:** captura de una clase de test con comentarios Given/When/Then (por ejemplo `UsuarioServiceTest` o `CitaRepositoryTest`)
3. **Terminal:** captura con `BUILD SUCCESS` y resumen `Tests run: N, Failures: 0, Errors: 0`
