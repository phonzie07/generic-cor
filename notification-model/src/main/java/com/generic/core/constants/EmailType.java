package com.generic.core.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The type Email type.
 */
@Component
@ConfigurationProperties( prefix = "constants.email.type" )
public class EmailType {

    /**
     * The constant SENDLOCL.
     */
    public static String SENDLOCL;

    /**
     * The constant SEMDLOF.
     */
    public static String SEMDLOF;

    /**
     * The constant SENDC.
     */
    public static String SENDC;

    /**
     * The constant SENFS.
     */
    public static String SENFS;

    /**
     * The constant SENFDB.
     */
    public static String SENFDB;

    /**
     * The constant SENFE.
     */
    public static String SENFE;

    /**
     * The constant SENFCDP.
     */
    public static String SENFCDP;

    /**
     * The constant SNBR.
     */
    public static String SNBR;

    /**
     * The constant SBS.
     */
    public static String SBS;

    /**
     * The constant SAR.
     */
    public static String SAR;

    /**
     * The constant SENFPCR.
     */
    public static String SENFPCR; //senfpcr

    /**
     * The constant SENCPCR.
     */
    public static String SENCPCR; //sencpcr

    /**
     * The constant SENCOCFP.
     */
    public static String SENCOCFP; //sencocfp

    /**
     * The constant SENCOCOFP.
     */
    public static String SENCOCOFP; //sencocofp

    /**
     * The constant SENCOCS.
     */
    public static String SENCOCS; //sencocs

    /**
     * The constant SENCOSP.
     */
    public static String SENCOSP; //sencosp

    /**
     * The constant SENCOIP.
     */
    public static String SENCOIP; //sencoip

    /**
     * The constant SENCOSO.
     */
    public static String SENCOSO; //sencoso

    /**
     * Sets sendlocl.
     *
     * @param SENDLOCL the sendlocl
     */
    public void setSENDLOCL( String SENDLOCL ) {
        EmailType.SENDLOCL = SENDLOCL;
    }

    /**
     * Sets semdlof.
     *
     * @param SEMDLOF the semdlof
     */
    public void setSEMDLOF( String SEMDLOF ) {
        EmailType.SEMDLOF = SEMDLOF;
    }

    /**
     * Sets sendc.
     *
     * @param SENDC the sendc
     */
    public void setSENDC( String SENDC ) {
        EmailType.SENDC = SENDC;
    }

    /**
     * Sets senfs.
     *
     * @param SENFS the senfs
     */
    public void setSENFS( String SENFS ) {
        EmailType.SENFS = SENFS;
    }

    /**
     * Sets senfdb.
     *
     * @param SENFDB the senfdb
     */
    public void setSENFDB( String SENFDB ) {
        EmailType.SENFDB = SENFDB;
    }

    /**
     * Sets senfe.
     *
     * @param SENFE the senfe
     */
    public void setSENFE( String SENFE ) {
        EmailType.SENFE = SENFE;
    }

    /**
     * Sets senfcdp.
     *
     * @param SENFCDP the senfcdp
     */
    public void setSENFCDP( String SENFCDP ) {
        EmailType.SENFCDP = SENFCDP;
    }

    /**
     * Sets snbr.
     *
     * @param SNBR the snbr
     */
    public void setSNBR( String SNBR ) {
        EmailType.SNBR = SNBR;
    }

    /**
     * Sets sbs.
     *
     * @param SBS the sbs
     */
    public void setSBS( String SBS ) {
        EmailType.SBS = SBS;
    }

    /**
     * Sets sar.
     *
     * @param SAR the sar
     */
    public void setSAR( String SAR ) {
        EmailType.SAR = SAR;
    }

    /**
     * Sets senfpcr.
     *
     * @param SENFPCR the senfpcr
     */
    public void setSENFPCR( String SENFPCR ) {
        EmailType.SENFPCR = SENFPCR;
    }

    /**
     * Sets sencpcr.
     *
     * @param SENCPCR the sencpcr
     */
    public void setSENCPCR( String SENCPCR ) {
        EmailType.SENCPCR = SENCPCR;
    }

    /**
     * Sets sencocfp.
     *
     * @param SENCOCFP the sencocfp
     */
    public void setSENCOCFP( String SENCOCFP ) {
        EmailType.SENCOCFP = SENCOCFP;
    }

    /**
     * Sets sencocofp.
     *
     * @param SENCOCOFP the sencocofp
     */
    public void setSENCOCOFP( String SENCOCOFP ) {
        EmailType.SENCOCOFP = SENCOCOFP;
    }

    /**
     * Sets sencocs.
     *
     * @param SENCOCS the sencocs
     */
    public void setSENCOCS( String SENCOCS ) {
        EmailType.SENCOCS = SENCOCS;
    }

    /**
     * Sets sencosp.
     *
     * @param SENCOSP the sencosp
     */
    public void setSENCOSP( String SENCOSP ) {
        EmailType.SENCOSP = SENCOSP;
    }

    /**
     * Sets sencoip.
     *
     * @param SENCOIP the sencoip
     */
    public void setSENCOIP( String SENCOIP ) {
        EmailType.SENCOIP = SENCOIP;
    }

    /**
     * Sets sencoso.
     *
     * @param SENCOSO the sencoso
     */
    public void setSENCOSO( String SENCOSO ) {
        EmailType.SENCOSO = SENCOSO;
    }

}
