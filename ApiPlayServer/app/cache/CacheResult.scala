package cache

import play.api.cache.CacheApi

trait CacheResult {

  val cache: CacheApi

  def cachePositiveResult[E, T](key: String)(block: => Either[E, T])(implicit ev: scala.reflect.ClassTag[T]) = cache.get[T](key) match {
    case Some(cached) => Right(cached)
    case None => block match {
      case Right(result) => {
        cache.set(key, result)
        Right(result)
      }
      case error => error
    }
  }
  
  def notSupported: Nothing = throw new UnsupportedOperationException("Operation not supported in cached implementation")
}