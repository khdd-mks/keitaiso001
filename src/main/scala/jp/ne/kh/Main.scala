package jp.ne.kh

object Main {
  def main(args:Array[String]):Unit = {
    import scalikejdbc._, async._
    import scala.concurrent._, duration._, ExecutionContext.Implicits.global

    if (args.length < 3) throw new IllegalArgumentException("Needs JDBC Connection String / User Name / Password")

    val jdbcConnectionString = args(0)
    val userName = args(1)
    val password = args(2)
    // set up connection pool (that's all you need to do)
    AsyncConnectionPool.singleton(jdbcConnectionString, userName, password)

    // create a new record within a transaction
    val tweets : Future[List[Tweet]] = AsyncDB.localTx { implicit tx =>
      Tweet.findAll()
    }

    Await.result(tweets, 60.seconds)
    tweets.foreach(_.foreach(println))
  }
}

