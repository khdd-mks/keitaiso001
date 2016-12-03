package jp.ne.kh

import scalikejdbc._, async._, FutureImplicits._
import scala.concurrent._
import org.joda.time.DateTime

case class Tweet(
  id : String,
  text : Option[String] = None,
  createdAt : Option[DateTime] = None,
  user : Option[String] = None,
  source : Option[String] = None,
  contributors : Option[String] = None,
  currentUserRetweetId : Option[Long] = None,
  favoriteCount : Option[Int] = None,
  geolocation : Option[String] = None,
  inReplyToScreenName : Option[String] = None,
  inReplyToStatusId : Option[String] = None,
  inReplyToUserId : Option[String] = None,
  lang : Option[String] = None,
  place : Option[String] = None,
  quotedStatus : Option[String] = None,
  quotedStatusId : Option[String] = None,
  retweetCount : Option[Int] = None,
  retweetedStatus : Option[String] = None,
  scopes : Option[String] = None,
  withHeldInCountries : Option[String] = None,
  isFavorited : Option[Int] = None,
  isPossiblySensitive : Option[Int] = None,
  isRetweet : Option[Int] = None,
  isRetweeted : Option[Int] = None,
  isRetweetedByMe : Option[Int] = None,
  isTruncated : Option[Int] = None,
  isTreated : Int,
  treatedTime : DateTime,
  createTime : DateTime,
  updateTime : DateTime
) extends ShortenedNames {

}

object Tweet extends SQLSyntaxSupport[Tweet] with ShortenedNames {

  override val columnNames = Seq(
            "id", "text", "createdat", "user", "source", "contributors",
            "currentuserretweetid", "favoritecount", "geolocation", "inreplytoscreenname",
            "inreplytostatusid", "inreplytouserid", "lang", "place", "quotedstatus",
            "quotedstatusid", "retweetcount", "retweetedstatus", "scopes", "withheldincountries",
            "isfavorited", "ispossiblysensitive", "isretweet", "isretweeted", "isretweetedbyme",
            "istruncated", "is_treated", "treated_time", "create_time", "update_time"
  )
  override val tableName = "t_twitter_status"
  override val nameConverters = Map(
            "^createdAt$" -> "createdat",
            "^currentUserRetweetId$" -> "currentuserretweetid",
            "^favoriteCount$" -> "favoritecount",
            "^inReplyToScreenName$" -> "inreplytoscreenname",
            "^inReplyToStatusId$" -> "inreplytostatusid",
            "^inReplyToUserId$" -> "inreplytouserid",
            "^quotedStatus$" -> "quotedstatus",
            "^quotedStatusId$" -> "quotedstatusid",
            "^retweetCount$" -> "retweetcount",
            "^retweetedStatus$" -> "retweetedstatus",
            "^withHeldInCountries$" -> "withheldincountries",
            "^isFavorited$" -> "isfavorited",
            "^isPossiblySensitive$" -> "ispossiblysensitive",
            "^isRetweet$" -> "isretweet",
            "^isRetweeted$" -> "isretweeted",
            "^isRetweetedByMe$" -> "isretweetedbyme",
            "^isTruncated$" -> "istruncated",
            "^isTreated$" -> "is_treated",
            "^treatedTime$" -> "treated_time",
            "^createTime$" -> "create_time",
            "^updateTime$" -> "update_time"
  )

  def apply(t: SyntaxProvider[Tweet])(rs: WrappedResultSet): Tweet = apply(t.resultName)(rs)
  def apply(t: ResultName[Tweet])(rs: WrappedResultSet): Tweet = new Tweet(
    id = rs.string(t.id),
    text = rs.stringOpt(t.text),
    createdAt = rs.jodaDateTimeOpt(t.createdAt),
    user = rs.stringOpt(t.user),
    source = rs.stringOpt(t.source),
    contributors = rs.stringOpt(t.contributors),
    currentUserRetweetId = rs.longOpt(t.currentUserRetweetId),
    favoriteCount = rs.intOpt(t.favoriteCount),
    geolocation = rs.stringOpt(t.geolocation),
    inReplyToScreenName = rs.stringOpt(t.inReplyToScreenName),
    inReplyToStatusId = rs.stringOpt(t.inReplyToStatusId),
    inReplyToUserId = rs.stringOpt(t.inReplyToUserId),
    lang = rs.stringOpt(t.lang),
    place = rs.stringOpt(t.place),
    quotedStatus = rs.stringOpt(t.quotedStatus),
    quotedStatusId = rs.stringOpt(t.quotedStatusId),
    retweetCount = rs.intOpt(t.retweetCount),
    retweetedStatus = rs.stringOpt(t.retweetedStatus),
    scopes = rs.stringOpt(t.scopes),
    withHeldInCountries = rs.stringOpt(t.withHeldInCountries),
    isFavorited = rs.intOpt(t.isFavorited),
    isPossiblySensitive = rs.intOpt(t.isPossiblySensitive),
    isRetweet = rs.intOpt(t.isRetweet),
    isRetweeted = rs.intOpt(t.isRetweeted),
    isRetweetedByMe = rs.intOpt(t.isRetweetedByMe),
    isTruncated = rs.intOpt(t.isTruncated),
    isTreated = rs.int(t.isTreated),
    treatedTime = rs.jodaDateTime(t.treatedTime),
    createTime = rs.jodaDateTime(t.createTime),
    updateTime = rs.jodaDateTime(t.updateTime)
  )

  lazy val t = Tweet.syntax("t")

  def find(id: String)(implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[Option[Tweet]] = withSQL {
    select.from(Tweet as t).where.eq(t.id, id)
  }.map(Tweet(t))

  def findAll()(implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[List[Tweet]] = withSQL {
    select.from(Tweet as t)
      .orderBy(t.id)
  }.map(Tweet(t))

  def countAll()(implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[Long] = withSQL {
    select(sqls.count).from(Tweet as t)
  }.map(rs => rs.long(1)).single.future.map(_.get)

  def findAllBy(where: SQLSyntax)(
    implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[List[Tweet]] = withSQL {
    select.from(Tweet as t)
      .where.append(sqls"${where}")
      .orderBy(t.id)
  }.map(Tweet(t))

  def countBy(where: SQLSyntax)(
    implicit session: AsyncDBSession = AsyncDB.sharedSession, cxt: EC = ECGlobal): Future[Long] = withSQL {
    select(sqls.count).from(Tweet as t).where.append(sqls"${where}")
  }.map(_.long(1)).single.future.map(_.get)
}

