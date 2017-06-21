package org.theflies.registry.web.errors

import org.springframework.core.annotation.AnnotationUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus


/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 */
@ControllerAdvice
class ExceptionTranslator {

  @ExceptionHandler(MethodArgumentNotValidException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  fun processValidationError(ex: MethodArgumentNotValidException): ErrorVM {
    val result = ex.bindingResult
    val fieldErrors = result.fieldErrors
    val dto = ErrorVM(ErrorConstants.ERR_VALIDATION)
    for (fieldError in fieldErrors) {
      dto.add(fieldError.objectName, fieldError.field, fieldError.code)
    }
    return dto
  }

  @ExceptionHandler(CustomParameterizedException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  fun processParameterizedValidationError(ex: CustomParameterizedException): ParameterizedErrorVM =
      ex.errorVM

  @ExceptionHandler(AccessDeniedException::class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  fun processAccessDeniedException(e: AccessDeniedException): ErrorVM =
      ErrorVM(ErrorConstants.ERR_ACCESS_DENIED, e.message)

  @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
  @ResponseBody
  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  fun processMethodNotSupportedException(exception: HttpRequestMethodNotSupportedException): ErrorVM =
      ErrorVM(ErrorConstants.ERR_METHOD_NOT_SUPPORTED, exception.message)

  @ExceptionHandler(Exception::class)
  fun processRuntimeException(ex: Exception): ResponseEntity<ErrorVM> {
    val builder: ResponseEntity.BodyBuilder
    val errorVM: ErrorVM
    val responseStatus = AnnotationUtils.findAnnotation(ex.javaClass, ResponseStatus::class.java)
    if (responseStatus != null) {
      builder = ResponseEntity.status(responseStatus.value)
      errorVM = ErrorVM("error." + responseStatus.value.value(), responseStatus.reason)
    } else {
      builder = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      errorVM = ErrorVM(ErrorConstants.ERR_INTERNAL_SERVER_ERROR, "Internal server error")
    }
    return builder.body(errorVM)
  }
}
