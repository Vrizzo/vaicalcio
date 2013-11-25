package it.newmedia.gokick.data.hibernate.beans;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * Classe che rappresenta l'oggetto Pitch che fa riferimento alla tabella PITCHES.
 *
 * @hibernate.class
 * table="PITCHES"
 */
public class Pitch extends ABean
{

  private SportCenter sportCenter;
  private PitchCover pitchCover;
  private PitchGround pitchGround; 
  private PitchType pitchType;
  private MatchType matchType;
  private Set<Sport> sportList = new HashSet<Sport>();
  

  /**
   * @hibernate.id
   * column="ID_PITCH"
   * generator-class="native"
   * unsaved-value="null"
   */
  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  /**
   * @hibernate.many-to-one
   * column="ID_MATCH_TYPE"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * outer-join="true"
   * class="it.newmedia.gokick.data.hibernate.beans.MatchType"
   */
    public MatchType getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchType matchType) {
        this.matchType = matchType;
    }

     /**
   * @hibernate.many-to-one
   * column="ID_PITCH_COVER"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * outer-join="true"
   * class="it.newmedia.gokick.data.hibernate.beans.PitchCover"
   */
    public PitchCover getPitchCover() {
        return pitchCover;
    }

    public void setPitchCover(PitchCover pitchCover) {
        this.pitchCover = pitchCover;
    }

      /**
   * @hibernate.many-to-one
   * column="ID_PITCH_GROUND"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * outer-join="true"
   * class="it.newmedia.gokick.data.hibernate.beans.PitchGround"
   */
    public PitchGround getPitchGround() {
        return pitchGround;
    }

    public void setPitchGround(PitchGround pitchGround) {
        this.pitchGround = pitchGround;
    }

  /**
   * @hibernate.many-to-one
   * column="ID_PITCH_TYPE"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * outer-join="true"
   * class="it.newmedia.gokick.data.hibernate.beans.PitchType"
   */
    public PitchType getPitchType() {
        return pitchType;
    }

    public void setPitchType(PitchType pitchType) {
        this.pitchType = pitchType;
    }

   /**
   * @hibernate.many-to-one
   * column="ID_SPORTS_CENTER"
   * cascade="none"
   * not-null="false"
   * lazy="false"
   * class="it.newmedia.gokick.data.hibernate.beans.SportCenter"
   */
    public SportCenter getSportCenter() {
        return sportCenter;
    }

    public void setSportCenter(SportCenter sportCenter) {
        this.sportCenter = sportCenter;
    }

    /**
     *
     * @return
     * @hibernate.set
     * table="PITCHES_TO_SPORTS"
     * cascade="none"
     * inverse="true"
     * lazy="true"
     *
     * @hibernate.key
     * column="ID_PITCH"
     *
     * @hibernate.many-to-many
     * class="it.newmedia.gokick.data.hibernate.beans.Sport"
     * column="ID_SPORT"
     */
    public Set<Sport> getSportList() {
        return sportList;
    }

    public void setSportList(Set<Sport> sportList) {
        this.sportList = sportList;
    }

    
}
