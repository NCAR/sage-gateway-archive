--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.1
-- Dumped by pg_dump version 9.1.1
-- Started on 2013-02-11 14:02:48

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 6 (class 2615 OID 31764098)
-- Name: metadata; Type: SCHEMA; Schema: -; Owner: esgcet_admin
--

CREATE SCHEMA metadata;


ALTER SCHEMA metadata OWNER TO esgcet_admin;

--
-- TOC entry 7 (class 2615 OID 31764099)
-- Name: metrics; Type: SCHEMA; Schema: -; Owner: esgcet_admin
--

CREATE SCHEMA metrics;


ALTER SCHEMA metrics OWNER TO esgcet_admin;

--
-- TOC entry 9 (class 2615 OID 31764100)
-- Name: security; Type: SCHEMA; Schema: -; Owner: esgcet_admin
--

CREATE SCHEMA security;


ALTER SCHEMA security OWNER TO esgcet_admin;

--
-- TOC entry 10 (class 2615 OID 31764101)
-- Name: workspace; Type: SCHEMA; Schema: -; Owner: esgcet_admin
--

CREATE SCHEMA workspace;


ALTER SCHEMA workspace OWNER TO esgcet_admin;

--
-- TOC entry 308 (class 3079 OID 31764102)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 3124 (class 0 OID 0)
-- Dependencies: 308
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = metadata, pg_catalog;

--
-- TOC entry 327 (class 1255 OID 31764107)
-- Dependencies: 1005 6
-- Name: dataset_version_update_resource_timestamp(); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION dataset_version_update_resource_timestamp() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN UPDATE metadata.resource set date_updated = public.default_timestamp() where id = NEW.dataset_id; return NULL; END;$$;


ALTER FUNCTION metadata.dataset_version_update_resource_timestamp() OWNER TO esgcet_admin;

--
-- TOC entry 321 (class 1255 OID 31764108)
-- Dependencies: 6 1005
-- Name: func_check_parent_display_order(); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION func_check_parent_display_order() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
  duplicate_order_count INTEGER;
BEGIN
 IF (NEW.parent_dataset_id IS NOT NULL) THEN
    IF (NEW.parent_display_order IS NULL) THEN
      RAISE EXCEPTION 'Cannot have null parent_display_order for a nested dataset [%].', NEW.id;
	END IF;
    SELECT COUNT(*) INTO duplicate_order_count FROM metadata.dataset AS dataset
      WHERE dataset.parent_dataset_id = NEW.parent_dataset_id AND dataset.parent_display_order = NEW.parent_display_order;
    
    IF (duplicate_order_count > 1) THEN
      RAISE EXCEPTION 'Cannot have multiple parent_display_order values for given parent.';
    END IF;
  ELSE
    IF (NEW.parent_display_order IS NOT NULL) THEN
      RAISE EXCEPTION 'Cannot have a parent_display_order for a top-level dataset.';
	END IF;
  END IF;
  RETURN NEW;
END;
$$;


ALTER FUNCTION metadata.func_check_parent_display_order() OWNER TO esgcet_admin;

--
-- TOC entry 322 (class 1255 OID 31764109)
-- Dependencies: 6 1005
-- Name: get_gateway_for_dataset(uuid); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION get_gateway_for_dataset(dataset_id uuid) RETURNS uuid
    LANGUAGE plpgsql
    AS $$

DECLARE
  gateway_id UUID;
BEGIN
  SELECT INTO gateway_id parent_gateway_id FROM metadata.dataset where id = metadata.get_top_dataset_parent(dataset_id);

  RETURN gateway_id;

END;
$$;


ALTER FUNCTION metadata.get_gateway_for_dataset(dataset_id uuid) OWNER TO esgcet_admin;

--
-- TOC entry 323 (class 1255 OID 31764110)
-- Dependencies: 6 1005
-- Name: get_top_dataset_parent(uuid); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION get_top_dataset_parent(dataset_id uuid) RETURNS uuid
    LANGUAGE plpgsql
    AS $$

DECLARE
  parent_id UUID;
BEGIN
  SELECT INTO parent_id parent_dataset_id FROM metadata.dataset where id = dataset_id;

  IF parent_id is NOT NULL THEN
    parent_id := metadata.get_top_dataset_parent(parent_id);
  ELSE
    parent_id = dataset_id;
  END IF;

  RETURN parent_id;

END;
$$;


ALTER FUNCTION metadata.get_top_dataset_parent(dataset_id uuid) OWNER TO esgcet_admin;

--
-- TOC entry 324 (class 1255 OID 31764111)
-- Dependencies: 1005 6
-- Name: logical_file_delete_handler(); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION logical_file_delete_handler() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  delete from metadata.resource where metadata.resource.id = OLD.id;
  RETURN OLD;
END;
$$;


ALTER FUNCTION metadata.logical_file_delete_handler() OWNER TO esgcet_admin;

--
-- TOC entry 320 (class 1255 OID 31764112)
-- Dependencies: 6 1005
-- Name: persistent_object_update_timestamps(); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION persistent_object_update_timestamps() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
 NEW.date_updated = public.default_timestamp();
 return NEW;
END;
$$;


ALTER FUNCTION metadata.persistent_object_update_timestamps() OWNER TO esgcet_admin;

--
-- TOC entry 325 (class 1255 OID 31764113)
-- Dependencies: 6 1005
-- Name: summarize_dataset_counts(); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION summarize_dataset_counts() RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
    top_datasets CURSOR FOR SELECT id FROM metadata.dataset;
    dataset_id integer;
BEGIN

  WHILE (FOUND) LOOP
	FETCH top_datasets INTO dataset_id;
	PERFORM metadata.update_dataset_counts(dataset_id);
  END LOOP;
  
  CLOSE top_datasets;
  return;
END;
$$;


ALTER FUNCTION metadata.summarize_dataset_counts() OWNER TO esgcet_admin;

--
-- TOC entry 326 (class 1255 OID 31764114)
-- Dependencies: 6 1005
-- Name: summarize_project_counts(); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION summarize_project_counts() RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
    top_datasets CURSOR FOR SELECT id FROM metadata.dataset WHERE project IS NOT NULL;
    dataset_id integer;
BEGIN

  WHILE (FOUND) LOOP
	FETCH top_datasets INTO dataset_id;
	PERFORM metadata.update_project_dataset_count(dataset_id);
  END LOOP;
  
  CLOSE top_datasets;
  return;
END;
$$;


ALTER FUNCTION metadata.summarize_project_counts() OWNER TO esgcet_admin;

--
-- TOC entry 328 (class 1255 OID 31764115)
-- Dependencies: 1005 6
-- Name: trigger_update_dataset_counts(); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION trigger_update_dataset_counts() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  IF (TG_OP = 'DELETE') THEN
    PERFORM metadata.update_dataset_counts(OLD.parent_dataset);
  ELSIF (TG_OP = 'INSERT') THEN
    PERFORM metadata.update_dataset_counts(NEW.parent_dataset);
  END IF;
  RETURN NULL;
END;
$$;


ALTER FUNCTION metadata.trigger_update_dataset_counts() OWNER TO esgcet_admin;

--
-- TOC entry 329 (class 1255 OID 31764116)
-- Dependencies: 1005 6
-- Name: trigger_update_project_counts(); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION trigger_update_project_counts() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  IF (NEW.project IS NOT NULL) THEN 
	  IF (TG_OP = 'DELETE') THEN
	    PERFORM metadata.update_project_dataset_count(OLD.project);
	  ELSIF (TG_OP = 'INSERT') THEN
	    PERFORM metadata.update_project_dataset_count(NEW.project);
	  END IF;
  END IF;
  
  RETURN NULL;

END;
$$;


ALTER FUNCTION metadata.trigger_update_project_counts() OWNER TO esgcet_admin;

--
-- TOC entry 330 (class 1255 OID 31764117)
-- Dependencies: 6 1005
-- Name: update_dataset_counts(integer); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION update_dataset_counts(target_dataset_id integer) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
  cdc integer;
  lfc integer;
BEGIN
  SELECT into cdc count(id) from metadata.dataset where parent_dataset = target_dataset_id;
  SELECT into lfc count(id) from metadata.logical_file where dataset = target_dataset_id;

  UPDATE metadata.dataset set nested_dataset_count=cdc, logical_file_count=lfc where id = target_dataset_id;

  RETURN;
END;
$$;


ALTER FUNCTION metadata.update_dataset_counts(target_dataset_id integer) OWNER TO esgcet_admin;

--
-- TOC entry 331 (class 1255 OID 31764118)
-- Dependencies: 6 1005
-- Name: update_project_dataset_count(bigint); Type: FUNCTION; Schema: metadata; Owner: esgcet_admin
--

CREATE FUNCTION update_project_dataset_count(target_project_id bigint) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
  dataset_count integer;
BEGIN
  SELECT into dataset_count count(id) from metadata.dataset where project = target_project_id;

  UPDATE metadata.project set nested_dataset_count = dataset_count where id = target_project_id;

  RETURN;
END;
$$;


ALTER FUNCTION metadata.update_project_dataset_count(target_project_id bigint) OWNER TO esgcet_admin;

SET search_path = metrics, pg_catalog;

--
-- TOC entry 332 (class 1255 OID 31764119)
-- Dependencies: 1005 7
-- Name: get_date_interval(integer, text); Type: FUNCTION; Schema: metrics; Owner: esgcet_admin
--

CREATE FUNCTION get_date_interval(integer, text) RETURNS interval
    LANGUAGE plpgsql
    AS $_$

BEGIN
    return CAST( CAST($1 as TEXT) || ' ' || $2 as INTERVAL);
END;
$_$;


ALTER FUNCTION metrics.get_date_interval(integer, text) OWNER TO esgcet_admin;

--
-- TOC entry 333 (class 1255 OID 31764120)
-- Dependencies: 7
-- Name: get_timestamp_sequence(date, date, text); Type: FUNCTION; Schema: metrics; Owner: esgcet_admin
--

CREATE FUNCTION get_timestamp_sequence(date, date, text) RETURNS SETOF timestamp without time zone
    LANGUAGE sql
    AS $_$

SELECT $1 - CAST('1 ' || $3 AS INTERVAL) + metrics.get_date_interval(number, $3) FROM metrics.Numbers WHERE $1 - CAST('1 ' || $3 AS INTERVAL) + metrics.get_date_interval(number, $3) <= $2 ORDER BY 1;
$_$;


ALTER FUNCTION metrics.get_timestamp_sequence(date, date, text) OWNER TO esgcet_admin;

--
-- TOC entry 338 (class 1255 OID 31764121)
-- Dependencies: 7 1005
-- Name: save_metrics(); Type: FUNCTION; Schema: metrics; Owner: esgcet_admin
--

CREATE FUNCTION save_metrics() RETURNS trigger
    LANGUAGE plpgsql
    AS $$		
		DECLARE
		    user_row security.user%ROWTYPE; 
		    fap_row metadata.file_access_point%ROWTYPE; 
		    logical_file_row metadata.logical_file%ROWTYPE; 
		BEGIN

		-- What I have (NEW values): 
		-- INSERT INTO metrics.file_download (user_openid, file_access_point_uri, date_started, date_completed, success, duration, user_agent_name) VALUES (?, ?, ?, ?, ?, ?, ?)

        -- Check that openid is given.  Possible that user is not logged in, so no openid.
        IF NEW.user_openid IS NOT NULL THEN     
      
           -- Look up values for User based on unique openid
           SELECT * INTO user_row from security.user where openid = NEW.user_openid;

           NEW.user_username := user_row.username;
           NEW.user_id := user_row.id;
           NEW.user_email := user_row.email;
           NEW.user_firstname := user_row.firstname;
           NEW.user_lastname := user_row.lastname;

        END IF;
        
		-- User Agent
		SELECT id INTO NEW.user_agent_id FROM metrics.user_agent WHERE name = NEW.user_agent_name; 

		-- Check for given File access point
		IF NEW.file_access_point_uri IS NULL THEN
		    RAISE EXCEPTION 'file_access_point_uri cannot be null'; 
		END IF; 

		SELECT * INTO fap_row FROM metadata.file_access_point where full_access_url = NEW.file_access_point_uri; 

		NEW.file_access_point_id := fap_row.id; 

		-- logical_file
		SELECT * INTO logical_file_row FROM metadata.logical_file where id = fap_row.logical_file_id; 

		NEW.logical_file_id := logical_file_row.id; 
		NEW.logical_file_name := logical_file_row.name; 
		NEW.logical_file_size := logical_file_row.size; 
		NEW.logical_file_lineage_id := logical_file_row.lineage_id; 
		NEW.logical_file_version_id := logical_file_row.version_id; 

		RETURN NEW; 
		END; $$;


ALTER FUNCTION metrics.save_metrics() OWNER TO esgcet_admin;

SET search_path = public, pg_catalog;

--
-- TOC entry 334 (class 1255 OID 31764122)
-- Dependencies: 1005 8
-- Name: afterstm_log(); Type: FUNCTION; Schema: public; Owner: esgcet_admin
--

CREATE FUNCTION afterstm_log() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  RAISE NOTICE 'Trigger statement row change for ID:';
  return NEW;
END;
$$;


ALTER FUNCTION public.afterstm_log() OWNER TO esgcet_admin;

--
-- TOC entry 335 (class 1255 OID 31764123)
-- Dependencies: 8 1005
-- Name: default_timestamp(); Type: FUNCTION; Schema: public; Owner: esgcet_admin
--

CREATE FUNCTION default_timestamp() RETURNS timestamp with time zone
    LANGUAGE plpgsql STABLE
    AS $$
BEGIN
  return transaction_timestamp();
END;
$$;


ALTER FUNCTION public.default_timestamp() OWNER TO esgcet_admin;

--
-- TOC entry 336 (class 1255 OID 31764124)
-- Dependencies: 1005 8
-- Name: log(); Type: FUNCTION; Schema: public; Owner: esgcet_admin
--

CREATE FUNCTION log() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  RAISE NOTICE 'Trigger row change for ID:%', NEW.id;

END;
$$;


ALTER FUNCTION public.log() OWNER TO esgcet_admin;

--
-- TOC entry 337 (class 1255 OID 31764125)
-- Dependencies: 8 1005
-- Name: log_call(); Type: FUNCTION; Schema: public; Owner: esgcet_admin
--

CREATE FUNCTION log_call() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
  RAISE NOTICE 'Trigger row change for ID:%', NEW.id;
  return NEW;
END;
$$;


ALTER FUNCTION public.log_call() OWNER TO esgcet_admin;

SET search_path = metadata, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 165 (class 1259 OID 31764126)
-- Dependencies: 2368 2369 6
-- Name: activity; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE activity (
    id uuid NOT NULL,
    version integer NOT NULL,
    activity_type_id integer NOT NULL,
    name character varying(255) NOT NULL,
    rdf_identifier text,
    description text,
    date_created timestamp with time zone DEFAULT public.default_timestamp() NOT NULL,
    date_updated timestamp with time zone DEFAULT public.default_timestamp() NOT NULL
);


ALTER TABLE metadata.activity OWNER TO esgcet_admin;

--
-- TOC entry 166 (class 1259 OID 31764134)
-- Dependencies: 6
-- Name: activity_link; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE activity_link (
    activity_id uuid NOT NULL,
    text character varying NOT NULL,
    uri character varying NOT NULL,
    idx_order integer NOT NULL,
    link_type_id integer
);


ALTER TABLE metadata.activity_link OWNER TO esgcet_admin;

--
-- TOC entry 167 (class 1259 OID 31764140)
-- Dependencies: 6
-- Name: activity_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE activity_type (
    id integer NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE metadata.activity_type OWNER TO esgcet_admin;

--
-- TOC entry 168 (class 1259 OID 31764143)
-- Dependencies: 6
-- Name: award; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE award (
    project_id uuid NOT NULL,
    award_number text NOT NULL,
    idx_order integer NOT NULL
);


ALTER TABLE metadata.award OWNER TO esgcet_admin;

--
-- TOC entry 169 (class 1259 OID 31764149)
-- Dependencies: 6
-- Name: broadcast_message; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE broadcast_message (
    id bigint NOT NULL,
    message_text text NOT NULL
);


ALTER TABLE metadata.broadcast_message OWNER TO esgcet_admin;

--
-- TOC entry 170 (class 1259 OID 31764155)
-- Dependencies: 6
-- Name: broadcast_message_sequence; Type: SEQUENCE; Schema: metadata; Owner: esgcet_admin
--

CREATE SEQUENCE broadcast_message_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE metadata.broadcast_message_sequence OWNER TO esgcet_admin;

--
-- TOC entry 171 (class 1259 OID 31764157)
-- Dependencies: 6
-- Name: cadis_descriptive_metadata; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE cadis_descriptive_metadata (
    id uuid NOT NULL,
    cadis_metadata_profile_name character varying(255),
    cadis_metadata_profile_version character varying(5),
    cadis_metadata_profile_creation_date timestamp with time zone,
    cadis_metadata_profile_revision_date timestamp with time zone,
    idn_node character varying,
    grant_number character varying,
    spatial_resolution character varying,
    read_me_file character varying
);


ALTER TABLE metadata.cadis_descriptive_metadata OWNER TO esgcet_admin;

--
-- TOC entry 172 (class 1259 OID 31764163)
-- Dependencies: 6
-- Name: cadis_descriptive_metadata_cadis_resolution_type_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE cadis_descriptive_metadata_cadis_resolution_type_xref (
    cadis_descriptive_metadata_id uuid NOT NULL,
    cadis_resolution_type_id integer NOT NULL,
    idx_order integer NOT NULL
);


ALTER TABLE metadata.cadis_descriptive_metadata_cadis_resolution_type_xref OWNER TO esgcet_admin;

--
-- TOC entry 173 (class 1259 OID 31764166)
-- Dependencies: 6
-- Name: cadis_project; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE cadis_project (
    id uuid NOT NULL,
    continuing_project_id uuid,
    continuation_of_project_id uuid,
    period_of_performance_start timestamp with time zone,
    period_of_performance_end timestamp with time zone
);


ALTER TABLE metadata.cadis_project OWNER TO esgcet_admin;

--
-- TOC entry 174 (class 1259 OID 31764169)
-- Dependencies: 6
-- Name: cadis_project_project_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE cadis_project_project_xref (
    cadis_project_id uuid NOT NULL,
    project_id uuid NOT NULL
);


ALTER TABLE metadata.cadis_project_project_xref OWNER TO esgcet_admin;

--
-- TOC entry 175 (class 1259 OID 31764172)
-- Dependencies: 6
-- Name: cadis_project_typed_contact; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE cadis_project_typed_contact (
    cadis_project_id uuid NOT NULL,
    contact_id uuid NOT NULL,
    contact_type_id integer NOT NULL,
    nsf_award_number character varying
);


ALTER TABLE metadata.cadis_project_typed_contact OWNER TO esgcet_admin;

--
-- TOC entry 176 (class 1259 OID 31764178)
-- Dependencies: 6
-- Name: cadis_resolution_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE cadis_resolution_type (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE metadata.cadis_resolution_type OWNER TO esgcet_admin;

--
-- TOC entry 177 (class 1259 OID 31764181)
-- Dependencies: 6
-- Name: campaign; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE campaign (
    id uuid NOT NULL
);


ALTER TABLE metadata.campaign OWNER TO esgcet_admin;

--
-- TOC entry 178 (class 1259 OID 31764184)
-- Dependencies: 6
-- Name: checksum; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE checksum (
    logical_file_id uuid NOT NULL,
    algorithm text NOT NULL,
    value text NOT NULL
);
ALTER TABLE ONLY checksum ALTER COLUMN logical_file_id SET STATISTICS 0;
ALTER TABLE ONLY checksum ALTER COLUMN algorithm SET STATISTICS 0;
ALTER TABLE ONLY checksum ALTER COLUMN value SET STATISTICS 0;


ALTER TABLE metadata.checksum OWNER TO esgcet_admin;

--
-- TOC entry 179 (class 1259 OID 31764190)
-- Dependencies: 6
-- Name: collection_note; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE collection_note (
    id uuid NOT NULL,
    note character varying NOT NULL
);


ALTER TABLE metadata.collection_note OWNER TO esgcet_admin;

--
-- TOC entry 180 (class 1259 OID 31764196)
-- Dependencies: 6
-- Name: contact; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE contact (
    id uuid NOT NULL,
    email character varying,
    first_name character varying NOT NULL,
    last_name character varying NOT NULL,
    middle_name character varying,
    contact_uri character varying,
    version integer NOT NULL,
    user_id uuid
);


ALTER TABLE metadata.contact OWNER TO esgcet_admin;

--
-- TOC entry 181 (class 1259 OID 31764202)
-- Dependencies: 6
-- Name: contact_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE contact_type (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE metadata.contact_type OWNER TO esgcet_admin;

--
-- TOC entry 182 (class 1259 OID 31764208)
-- Dependencies: 6
-- Name: coordinate_axis; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE coordinate_axis (
    id uuid NOT NULL,
    step double precision,
    size double precision,
    unit_id uuid,
    coordinate_type_id integer NOT NULL,
    geophysical_properties_id uuid NOT NULL
);


ALTER TABLE metadata.coordinate_axis OWNER TO esgcet_admin;

--
-- TOC entry 183 (class 1259 OID 31764211)
-- Dependencies: 6
-- Name: coordinate_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE coordinate_type (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE metadata.coordinate_type OWNER TO esgcet_admin;

--
-- TOC entry 184 (class 1259 OID 31764217)
-- Dependencies: 2370 6
-- Name: data_access_application; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_access_application (
    id uuid NOT NULL,
    version integer DEFAULT 0 NOT NULL,
    name text NOT NULL,
    description text
);
ALTER TABLE ONLY data_access_application ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY data_access_application ALTER COLUMN version SET STATISTICS 0;
ALTER TABLE ONLY data_access_application ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY data_access_application ALTER COLUMN description SET STATISTICS 0;


ALTER TABLE metadata.data_access_application OWNER TO esgcet_admin;

--
-- TOC entry 185 (class 1259 OID 31764224)
-- Dependencies: 2371 6
-- Name: data_access_application_protocol_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_access_application_protocol_xref (
    data_access_application_ref uuid,
    data_access_protocol_ref uuid,
    preference_order integer DEFAULT 0
);
ALTER TABLE ONLY data_access_application_protocol_xref ALTER COLUMN data_access_application_ref SET STATISTICS 0;
ALTER TABLE ONLY data_access_application_protocol_xref ALTER COLUMN data_access_protocol_ref SET STATISTICS 0;
ALTER TABLE ONLY data_access_application_protocol_xref ALTER COLUMN preference_order SET STATISTICS 0;


ALTER TABLE metadata.data_access_application_protocol_xref OWNER TO esgcet_admin;

--
-- TOC entry 186 (class 1259 OID 31764228)
-- Dependencies: 2372 2373 2374 2375 6
-- Name: data_access_capability; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_access_capability (
    id uuid NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    date_created timestamp with time zone DEFAULT public.default_timestamp(),
    date_updated timestamp with time zone DEFAULT public.default_timestamp(),
    name character varying(255) NOT NULL,
    description text,
    capability_type_id uuid NOT NULL,
    base_uri character varying(1024) NOT NULL,
    proxy_base_uri character varying(1024),
    requires_authorization_token boolean DEFAULT false,
    data_node_id uuid
);


ALTER TABLE metadata.data_access_capability OWNER TO esgcet_admin;

--
-- TOC entry 187 (class 1259 OID 31764238)
-- Dependencies: 2376 6
-- Name: data_access_capability_application_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_access_capability_application_xref (
    data_access_capability_ref uuid,
    data_access_application_ref uuid,
    preference_order integer DEFAULT 0
);
ALTER TABLE ONLY data_access_capability_application_xref ALTER COLUMN data_access_capability_ref SET STATISTICS 0;
ALTER TABLE ONLY data_access_capability_application_xref ALTER COLUMN data_access_application_ref SET STATISTICS 0;
ALTER TABLE ONLY data_access_capability_application_xref ALTER COLUMN preference_order SET STATISTICS 0;


ALTER TABLE metadata.data_access_capability_application_xref OWNER TO esgcet_admin;

--
-- TOC entry 188 (class 1259 OID 31764242)
-- Dependencies: 2377 6
-- Name: data_access_capability_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_access_capability_type (
    id uuid NOT NULL,
    version integer NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    protocol_id uuid,
    direct_file_access boolean DEFAULT false
);


ALTER TABLE metadata.data_access_capability_type OWNER TO esgcet_admin;

--
-- TOC entry 189 (class 1259 OID 31764249)
-- Dependencies: 2378 2379 6
-- Name: data_access_protocol; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_access_protocol (
    id uuid NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    protocol text NOT NULL,
    description text,
    direct_file_access boolean DEFAULT false NOT NULL
);
ALTER TABLE ONLY data_access_protocol ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY data_access_protocol ALTER COLUMN version SET STATISTICS 0;
ALTER TABLE ONLY data_access_protocol ALTER COLUMN protocol SET STATISTICS 0;
ALTER TABLE ONLY data_access_protocol ALTER COLUMN description SET STATISTICS 0;


ALTER TABLE metadata.data_access_protocol OWNER TO esgcet_admin;

--
-- TOC entry 190 (class 1259 OID 31764257)
-- Dependencies: 6
-- Name: data_format; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_format (
    id uuid NOT NULL,
    name character varying(128) NOT NULL,
    description text,
    version integer NOT NULL
);


ALTER TABLE metadata.data_format OWNER TO esgcet_admin;

--
-- TOC entry 191 (class 1259 OID 31764263)
-- Dependencies: 6
-- Name: data_product_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_product_type (
    id uuid NOT NULL,
    name character varying(128) NOT NULL,
    description text,
    version integer
);


ALTER TABLE metadata.data_product_type OWNER TO esgcet_admin;

--
-- TOC entry 192 (class 1259 OID 31764269)
-- Dependencies: 6
-- Name: data_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_type (
    id integer NOT NULL,
    name character varying NOT NULL,
    long_name character varying
);


ALTER TABLE metadata.data_type OWNER TO esgcet_admin;

--
-- TOC entry 193 (class 1259 OID 31764275)
-- Dependencies: 2380 6
-- Name: datanode; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE datanode (
    id uuid NOT NULL,
    version integer DEFAULT 1,
    name text NOT NULL,
    distinguished_name text NOT NULL,
    description text,
    metrics_service_endpoint text,
    federation_gateway_id uuid NOT NULL,
    last_metrics_harvest_date timestamp with time zone
);


ALTER TABLE metadata.datanode OWNER TO esgcet_admin;

--
-- TOC entry 194 (class 1259 OID 31764282)
-- Dependencies: 2381 2382 2383 2384 6
-- Name: dataset; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset (
    id uuid NOT NULL,
    parent_dataset_id uuid,
    parent_display_order integer,
    license_id uuid,
    model_component_id uuid,
    metadata_profile_id uuid,
    replica_source_gateway_name text,
    is_replica boolean DEFAULT false NOT NULL,
    institution_id uuid,
    is_remote boolean DEFAULT false NOT NULL,
    is_visible boolean DEFAULT true NOT NULL,
    persistent_identifier character varying(512) NOT NULL,
    authoritative_source text NOT NULL,
    author text,
    CONSTRAINT check_parent_dataset_reference CHECK ((id <> parent_dataset_id))
);


ALTER TABLE metadata.dataset OWNER TO esgcet_admin;

--
-- TOC entry 195 (class 1259 OID 31764292)
-- Dependencies: 6
-- Name: dataset_access_point; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_access_point (
    id uuid NOT NULL,
    end_point character varying,
    proxy_end_point character varying,
    dataset_id uuid NOT NULL,
    version integer,
    path character varying(1024),
    full_access_url text,
    data_access_capability_id uuid
);


ALTER TABLE metadata.dataset_access_point OWNER TO esgcet_admin;

--
-- TOC entry 196 (class 1259 OID 31764298)
-- Dependencies: 6
-- Name: dataset_activity_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_activity_xref (
    dataset_id uuid NOT NULL,
    activity_id uuid NOT NULL
);


ALTER TABLE metadata.dataset_activity_xref OWNER TO esgcet_admin;

--
-- TOC entry 197 (class 1259 OID 31764301)
-- Dependencies: 6
-- Name: dataset_citation; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_citation (
    id uuid NOT NULL,
    descriptive_metadata_id uuid NOT NULL,
    dataset_title character varying(255) NOT NULL,
    creator_contact_id uuid,
    release_date timestamp with time zone,
    publication_place character varying(255),
    publisher_contact_id uuid,
    citation_version character varying(255),
    idx_order integer NOT NULL
);


ALTER TABLE metadata.dataset_citation OWNER TO esgcet_admin;

--
-- TOC entry 198 (class 1259 OID 31764307)
-- Dependencies: 6
-- Name: dataset_contact_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_contact_xref (
    dataset_id uuid NOT NULL,
    contact_type_id integer NOT NULL,
    contact_id uuid NOT NULL
);
ALTER TABLE ONLY dataset_contact_xref ALTER COLUMN dataset_id SET STATISTICS 0;
ALTER TABLE ONLY dataset_contact_xref ALTER COLUMN contact_type_id SET STATISTICS 0;
ALTER TABLE ONLY dataset_contact_xref ALTER COLUMN contact_id SET STATISTICS 0;


ALTER TABLE metadata.dataset_contact_xref OWNER TO esgcet_admin;

--
-- TOC entry 199 (class 1259 OID 31764310)
-- Dependencies: 6
-- Name: dataset_data_format_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_data_format_xref (
    dataset_id uuid NOT NULL,
    data_format_id uuid NOT NULL
);


ALTER TABLE metadata.dataset_data_format_xref OWNER TO esgcet_admin;

--
-- TOC entry 200 (class 1259 OID 31764313)
-- Dependencies: 6
-- Name: dataset_progress; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_progress (
    name character varying(128) NOT NULL,
    description character varying,
    id integer NOT NULL
);


ALTER TABLE metadata.dataset_progress OWNER TO esgcet_admin;

--
-- TOC entry 201 (class 1259 OID 31764319)
-- Dependencies: 6
-- Name: dataset_restriction; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_restriction (
    id uuid NOT NULL,
    idx_index integer NOT NULL,
    restriction character varying NOT NULL
);


ALTER TABLE metadata.dataset_restriction OWNER TO esgcet_admin;

--
-- TOC entry 202 (class 1259 OID 31764325)
-- Dependencies: 6
-- Name: dataset_topic_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_topic_xref (
    dataset_id uuid NOT NULL,
    topic_id uuid NOT NULL
);


ALTER TABLE metadata.dataset_topic_xref OWNER TO esgcet_admin;

--
-- TOC entry 203 (class 1259 OID 31764328)
-- Dependencies: 6
-- Name: dataset_typed_contact; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_typed_contact (
    dataset_id uuid NOT NULL,
    contact_id uuid NOT NULL,
    contact_type_id integer NOT NULL,
    idx_order integer
);


ALTER TABLE metadata.dataset_typed_contact OWNER TO esgcet_admin;

--
-- TOC entry 204 (class 1259 OID 31764331)
-- Dependencies: 2385 6
-- Name: dataset_version; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_version (
    id uuid NOT NULL,
    version bigint NOT NULL,
    version_id text NOT NULL,
    dataset_id uuid NOT NULL,
    date_created timestamp with time zone DEFAULT public.default_timestamp() NOT NULL,
    publisher uuid,
    comment text,
    label text,
    index integer,
    published_state_id integer NOT NULL,
    source_catalog_uri text
);


ALTER TABLE metadata.dataset_version OWNER TO esgcet_admin;

--
-- TOC entry 206 (class 1259 OID 31764342)
-- Dependencies: 6
-- Name: dataset_version_variable_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE dataset_version_variable_xref (
    dataset_version_id uuid NOT NULL,
    variable_id uuid NOT NULL
);


ALTER TABLE metadata.dataset_version_variable_xref OWNER TO esgcet_admin;

--
-- TOC entry 207 (class 1259 OID 31764345)
-- Dependencies: 6
-- Name: descriptive_metadata; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE descriptive_metadata (
    id uuid NOT NULL,
    language text,
    data_product_type_id uuid,
    physical_domain_id uuid,
    resolution_type_id integer,
    dataset_progress_id integer
);


ALTER TABLE metadata.descriptive_metadata OWNER TO esgcet_admin;

--
-- TOC entry 208 (class 1259 OID 31764351)
-- Dependencies: 6
-- Name: descriptive_metadata_data_type_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE descriptive_metadata_data_type_xref (
    descriptive_metadata_id uuid NOT NULL,
    data_type_id integer NOT NULL
);


ALTER TABLE metadata.descriptive_metadata_data_type_xref OWNER TO esgcet_admin;

--
-- TOC entry 209 (class 1259 OID 31764354)
-- Dependencies: 6
-- Name: descriptive_metadata_instrument_type_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE descriptive_metadata_instrument_type_xref (
    descriptive_metadata_id uuid NOT NULL,
    instrument_type_id uuid NOT NULL
);


ALTER TABLE metadata.descriptive_metadata_instrument_type_xref OWNER TO esgcet_admin;

--
-- TOC entry 210 (class 1259 OID 31764357)
-- Dependencies: 6
-- Name: descriptive_metadata_link; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE descriptive_metadata_link (
    descriptive_metadata_id uuid NOT NULL,
    text character varying NOT NULL,
    uri character varying NOT NULL,
    idx_order integer NOT NULL,
    link_type_id integer
);


ALTER TABLE metadata.descriptive_metadata_link OWNER TO esgcet_admin;

--
-- TOC entry 211 (class 1259 OID 31764363)
-- Dependencies: 6
-- Name: descriptive_metadata_location_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE descriptive_metadata_location_xref (
    descriptive_metadata_id uuid NOT NULL,
    location_id uuid NOT NULL
);


ALTER TABLE metadata.descriptive_metadata_location_xref OWNER TO esgcet_admin;

--
-- TOC entry 212 (class 1259 OID 31764366)
-- Dependencies: 6
-- Name: descriptive_metadata_platform_type_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE descriptive_metadata_platform_type_xref (
    descriptive_metadata_id uuid NOT NULL,
    platform_type_id uuid NOT NULL
);


ALTER TABLE metadata.descriptive_metadata_platform_type_xref OWNER TO esgcet_admin;

--
-- TOC entry 213 (class 1259 OID 31764369)
-- Dependencies: 6
-- Name: descriptive_metadata_time_frequency_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE descriptive_metadata_time_frequency_xref (
    descriptive_metadata_id uuid NOT NULL,
    time_frequency_id uuid NOT NULL
);


ALTER TABLE metadata.descriptive_metadata_time_frequency_xref OWNER TO esgcet_admin;

--
-- TOC entry 214 (class 1259 OID 31764372)
-- Dependencies: 6
-- Name: ensemble; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE ensemble (
    id uuid NOT NULL
);


ALTER TABLE metadata.ensemble OWNER TO esgcet_admin;

SET default_with_oids = true;

--
-- TOC entry 215 (class 1259 OID 31764375)
-- Dependencies: 6
-- Name: events; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE events (
    id uuid NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    event_type_id bigint NOT NULL
);


ALTER TABLE metadata.events OWNER TO esgcet_admin;

SET default_with_oids = false;

--
-- TOC entry 216 (class 1259 OID 31764378)
-- Dependencies: 6
-- Name: experiment; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE experiment (
    id uuid NOT NULL,
    short_name text,
    experiment_number text
);


ALTER TABLE metadata.experiment OWNER TO esgcet_admin;

--
-- TOC entry 217 (class 1259 OID 31764384)
-- Dependencies: 2386 6
-- Name: federation_gateway; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE federation_gateway (
    id uuid NOT NULL,
    version integer DEFAULT 1,
    name text NOT NULL,
    distinguished_name text NOT NULL,
    description text,
    base_endpoint text,
    base_secure_endpoint text,
    attributes_service_endpoint text,
    oai_repository_endpoint text,
    idp_endpoint text
);


ALTER TABLE metadata.federation_gateway OWNER TO esgcet_admin;

--
-- TOC entry 218 (class 1259 OID 31764391)
-- Dependencies: 2387 6
-- Name: file_access_point; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE file_access_point (
    id uuid NOT NULL,
    logical_file_id uuid NOT NULL,
    storage_type_id integer DEFAULT 0 NOT NULL,
    file_name character varying,
    version integer,
    path character varying(1024),
    full_access_url text,
    data_access_capability_id uuid NOT NULL,
    replica_id uuid
);


ALTER TABLE metadata.file_access_point OWNER TO esgcet_admin;

--
-- TOC entry 219 (class 1259 OID 31764398)
-- Dependencies: 2388 6
-- Name: forcing; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE forcing (
    id uuid NOT NULL,
    version integer DEFAULT 1 NOT NULL,
    value character varying(256),
    description text,
    type integer NOT NULL,
    name character varying(256) NOT NULL
);


ALTER TABLE metadata.forcing OWNER TO esgcet_admin;

--
-- TOC entry 220 (class 1259 OID 31764405)
-- Dependencies: 6
-- Name: forcing_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE forcing_type (
    id integer NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE metadata.forcing_type OWNER TO esgcet_admin;

--
-- TOC entry 221 (class 1259 OID 31764408)
-- Dependencies: 2389 2390 2391 6
-- Name: gateway; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE gateway (
    id uuid NOT NULL,
    name character varying(255),
    description text,
    system_message text,
    base_url text,
    base_secure_url text,
    attributes_service_url text,
    oai_repository_url text,
    identity text,
    project_view_handler_name text DEFAULT 'defaultProjectViewHandler'::text,
    collection_view_handler_name text DEFAULT 'defaultCollectionViewHandler'::text,
    metadata_profile_id uuid,
    administrator_personal character varying(100) NOT NULL,
    administrator_email character varying(100) NOT NULL,
    myproxy_endpoint text,
    idp_endpoint text,
    version integer DEFAULT 1
);


ALTER TABLE metadata.gateway OWNER TO esgcet_admin;

--
-- TOC entry 222 (class 1259 OID 31764417)
-- Dependencies: 2392 6
-- Name: gateway_specific_metadata; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE gateway_specific_metadata (
    id uuid NOT NULL,
    version integer DEFAULT 1 NOT NULL,
    dataset_ref uuid NOT NULL
);


ALTER TABLE metadata.gateway_specific_metadata OWNER TO esgcet_admin;

--
-- TOC entry 223 (class 1259 OID 31764421)
-- Dependencies: 6
-- Name: geophysical_properties; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE geophysical_properties (
    id uuid NOT NULL,
    grid_id uuid
);


ALTER TABLE metadata.geophysical_properties OWNER TO esgcet_admin;

--
-- TOC entry 224 (class 1259 OID 31764424)
-- Dependencies: 6
-- Name: geospatial_coordinate_axis; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE geospatial_coordinate_axis (
    start_range double precision NOT NULL,
    end_range double precision NOT NULL,
    id uuid NOT NULL
);


ALTER TABLE metadata.geospatial_coordinate_axis OWNER TO esgcet_admin;

--
-- TOC entry 225 (class 1259 OID 31764427)
-- Dependencies: 2393 6
-- Name: grid; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE grid (
    id uuid NOT NULL,
    name character varying(128) NOT NULL,
    description text,
    version integer DEFAULT 1
);


ALTER TABLE metadata.grid OWNER TO esgcet_admin;

--
-- TOC entry 3187 (class 0 OID 0)
-- Dependencies: 225
-- Name: TABLE grid; Type: COMMENT; Schema: metadata; Owner: esgcet_admin
--

COMMENT ON TABLE grid IS 'Table to hold Grid objects';


--
-- TOC entry 226 (class 1259 OID 31764434)
-- Dependencies: 6
-- Name: institution; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE institution (
    id uuid NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE metadata.institution OWNER TO esgcet_admin;

--
-- TOC entry 227 (class 1259 OID 31764437)
-- Dependencies: 6
-- Name: instrument_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE instrument_type (
    short_name character varying(128) NOT NULL,
    id uuid NOT NULL,
    version integer NOT NULL,
    long_name text
);


ALTER TABLE metadata.instrument_type OWNER TO esgcet_admin;

--
-- TOC entry 228 (class 1259 OID 31764443)
-- Dependencies: 6
-- Name: license; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE license (
    id uuid NOT NULL,
    version integer NOT NULL,
    name text NOT NULL,
    text text NOT NULL
);
ALTER TABLE ONLY license ALTER COLUMN id SET STATISTICS 0;
ALTER TABLE ONLY license ALTER COLUMN version SET STATISTICS 0;
ALTER TABLE ONLY license ALTER COLUMN name SET STATISTICS 0;
ALTER TABLE ONLY license ALTER COLUMN text SET STATISTICS 0;


ALTER TABLE metadata.license OWNER TO esgcet_admin;

--
-- TOC entry 229 (class 1259 OID 31764449)
-- Dependencies: 6
-- Name: link_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE link_type (
    id integer NOT NULL,
    long_name character varying(128)
);


ALTER TABLE metadata.link_type OWNER TO esgcet_admin;

--
-- TOC entry 230 (class 1259 OID 31764452)
-- Dependencies: 2394 6
-- Name: location; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE location (
    id uuid NOT NULL,
    version integer,
    name character varying DEFAULT 255 NOT NULL,
    description character varying,
    taxonomy_id integer NOT NULL
);


ALTER TABLE metadata.location OWNER TO esgcet_admin;

--
-- TOC entry 231 (class 1259 OID 31764459)
-- Dependencies: 2395 2396 2397 6
-- Name: logical_file; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE logical_file (
    id uuid NOT NULL,
    size bigint NOT NULL,
    data_format_id uuid,
    lineage_id text NOT NULL,
    version_id text,
    cmor_tracking_id text,
    label text,
    version integer,
    name character varying(512),
    description character varying(512),
    date_created timestamp with time zone DEFAULT public.default_timestamp(),
    date_updated timestamp with time zone DEFAULT public.default_timestamp(),
    CONSTRAINT chk_logical_file_size CHECK ((size >= 0))
);


ALTER TABLE metadata.logical_file OWNER TO esgcet_admin;

--
-- TOC entry 232 (class 1259 OID 31764468)
-- Dependencies: 6
-- Name: logical_file_dataset_version_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE logical_file_dataset_version_xref (
    logical_file_id uuid NOT NULL,
    dataset_version_id uuid NOT NULL
);


ALTER TABLE metadata.logical_file_dataset_version_xref OWNER TO esgcet_admin;

SET default_with_oids = true;

--
-- TOC entry 233 (class 1259 OID 31764471)
-- Dependencies: 6
-- Name: logical_file_variable_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE logical_file_variable_xref (
    logical_file_id uuid NOT NULL,
    variable_id uuid NOT NULL
);


ALTER TABLE metadata.logical_file_variable_xref OWNER TO esgcet_admin;

--
-- TOC entry 234 (class 1259 OID 31764474)
-- Dependencies: 6
-- Name: metadata_profile; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE metadata_profile (
    id uuid NOT NULL,
    version integer,
    profile_name text NOT NULL
);


ALTER TABLE metadata.metadata_profile OWNER TO esgcet_admin;

SET default_with_oids = false;

--
-- TOC entry 235 (class 1259 OID 31764480)
-- Dependencies: 6
-- Name: model; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE model (
    id uuid NOT NULL
);


ALTER TABLE metadata.model OWNER TO esgcet_admin;

--
-- TOC entry 236 (class 1259 OID 31764483)
-- Dependencies: 2398 2399 2400 2401 6
-- Name: model_component; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE model_component (
    id uuid NOT NULL,
    physical_domain_ref uuid,
    configured boolean DEFAULT false NOT NULL,
    unconfigured_model_component_id uuid,
    software_version character varying(128),
    name character varying(512) NOT NULL,
    description text,
    date_created timestamp with time zone DEFAULT public.default_timestamp(),
    date_updated timestamp with time zone DEFAULT public.default_timestamp(),
    version integer DEFAULT 1
);


ALTER TABLE metadata.model_component OWNER TO esgcet_admin;

--
-- TOC entry 237 (class 1259 OID 31764493)
-- Dependencies: 6
-- Name: model_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE model_type (
    name character varying(256) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE metadata.model_type OWNER TO esgcet_admin;

--
-- TOC entry 238 (class 1259 OID 31764496)
-- Dependencies: 6
-- Name: note; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE note (
    resource_ref uuid NOT NULL,
    text text NOT NULL
);


ALTER TABLE metadata.note OWNER TO esgcet_admin;

--
-- TOC entry 239 (class 1259 OID 31764502)
-- Dependencies: 6
-- Name: persistent_identifier; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE persistent_identifier (
    identifier character varying(512) NOT NULL,
    type_id bigint NOT NULL,
    resource_id uuid NOT NULL
);


ALTER TABLE metadata.persistent_identifier OWNER TO esgcet_admin;

--
-- TOC entry 240 (class 1259 OID 31764508)
-- Dependencies: 6
-- Name: persistent_indentifier_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE persistent_indentifier_type (
    id integer NOT NULL,
    name character varying(512) NOT NULL
);


ALTER TABLE metadata.persistent_indentifier_type OWNER TO esgcet_admin;

--
-- TOC entry 241 (class 1259 OID 31764514)
-- Dependencies: 6
-- Name: physical_domain; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE physical_domain (
    id uuid NOT NULL,
    name character varying(255),
    version integer
);


ALTER TABLE metadata.physical_domain OWNER TO esgcet_admin;

--
-- TOC entry 242 (class 1259 OID 31764517)
-- Dependencies: 6
-- Name: platform_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE platform_type (
    id uuid NOT NULL,
    version integer,
    short_name character varying(128) NOT NULL,
    long_name character varying
);


ALTER TABLE metadata.platform_type OWNER TO esgcet_admin;

--
-- TOC entry 243 (class 1259 OID 31764523)
-- Dependencies: 2402 6
-- Name: project; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE project (
    id uuid NOT NULL,
    project_url character varying(1024),
    dataset_id uuid,
    northern_latitude double precision,
    southern_latitude double precision,
    western_longitude double precision,
    eastern_longitude double precision,
    persistent_identifier character varying(512) NOT NULL,
    is_remote boolean DEFAULT false NOT NULL,
    continuing_project_id uuid,
    continuation_of_project_id uuid,
    period_of_performance_start timestamp with time zone,
    period_of_performance_end timestamp with time zone,
    project_group character varying(100)
);


ALTER TABLE metadata.project OWNER TO esgcet_admin;

--
-- TOC entry 244 (class 1259 OID 31764530)
-- Dependencies: 6
-- Name: published_state; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE published_state (
    id integer NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE metadata.published_state OWNER TO esgcet_admin;

--
-- TOC entry 245 (class 1259 OID 31764533)
-- Dependencies: 2403 6
-- Name: publishing_operation; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE publishing_operation (
    id uuid NOT NULL,
    version bigint,
    description character varying NOT NULL,
    task_results text NOT NULL,
    has_task_errors boolean NOT NULL,
    is_complete boolean NOT NULL,
    change_log_comment character varying NOT NULL,
    publishing_operation_type character varying NOT NULL,
    initiator uuid,
    date_created timestamp with time zone DEFAULT public.default_timestamp() NOT NULL
);


ALTER TABLE metadata.publishing_operation OWNER TO esgcet_admin;

--
-- TOC entry 246 (class 1259 OID 31764540)
-- Dependencies: 2404 2405 6
-- Name: replica; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE replica (
    id uuid NOT NULL,
    version bigint DEFAULT 0 NOT NULL,
    dataset_version_id uuid NOT NULL,
    source_catalog_uri text NOT NULL,
    date_created timestamp with time zone DEFAULT public.default_timestamp() NOT NULL
);


ALTER TABLE metadata.replica OWNER TO esgcet_admin;

--
-- TOC entry 247 (class 1259 OID 31764548)
-- Dependencies: 6
-- Name: resolution_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE resolution_type (
    id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE metadata.resolution_type OWNER TO esgcet_admin;

--
-- TOC entry 248 (class 1259 OID 31764551)
-- Dependencies: 2406 2407 2408 6
-- Name: resource; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE resource (
    id uuid NOT NULL,
    version integer DEFAULT 1,
    type_id integer NOT NULL,
    name character varying(512) NOT NULL,
    description text,
    date_created timestamp with time zone DEFAULT public.default_timestamp(),
    date_updated timestamp with time zone DEFAULT public.default_timestamp()
);


ALTER TABLE metadata.resource OWNER TO esgcet_admin;

--
-- TOC entry 249 (class 1259 OID 31764560)
-- Dependencies: 6
-- Name: resource_tag_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE resource_tag_xref (
    tag_ref uuid NOT NULL,
    resource_ref uuid NOT NULL
);


ALTER TABLE metadata.resource_tag_xref OWNER TO esgcet_admin;

--
-- TOC entry 250 (class 1259 OID 31764563)
-- Dependencies: 6
-- Name: resource_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE resource_type (
    id integer NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE metadata.resource_type OWNER TO esgcet_admin;

--
-- TOC entry 251 (class 1259 OID 31764566)
-- Dependencies: 6
-- Name: software_properties; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE software_properties (
    id uuid NOT NULL,
    software_version character varying(128)
);


ALTER TABLE metadata.software_properties OWNER TO esgcet_admin;

--
-- TOC entry 252 (class 1259 OID 31764569)
-- Dependencies: 2409 2410 6
-- Name: standard_name; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE standard_name (
    id uuid NOT NULL,
    version integer NOT NULL,
    date_created timestamp with time zone DEFAULT public.default_timestamp(),
    date_updated timestamp with time zone DEFAULT public.default_timestamp(),
    name character varying(255) NOT NULL,
    description text,
    type_id integer NOT NULL,
    units_id uuid
);


ALTER TABLE metadata.standard_name OWNER TO esgcet_admin;

--
-- TOC entry 253 (class 1259 OID 31764577)
-- Dependencies: 6
-- Name: standard_name_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE standard_name_type (
    id integer NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE metadata.standard_name_type OWNER TO esgcet_admin;

--
-- TOC entry 254 (class 1259 OID 31764580)
-- Dependencies: 6
-- Name: storage_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE storage_type (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE metadata.storage_type OWNER TO esgcet_admin;

--
-- TOC entry 255 (class 1259 OID 31764586)
-- Dependencies: 2411 2412 6
-- Name: tag; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE tag (
    id uuid NOT NULL,
    value character varying DEFAULT 255 NOT NULL,
    version integer DEFAULT 1
);


ALTER TABLE metadata.tag OWNER TO esgcet_admin;

--
-- TOC entry 256 (class 1259 OID 31764594)
-- Dependencies: 6
-- Name: taxonomy; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE taxonomy (
    name character varying(128) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE metadata.taxonomy OWNER TO esgcet_admin;

--
-- TOC entry 257 (class 1259 OID 31764597)
-- Dependencies: 6
-- Name: time_coordinate_axis; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE time_coordinate_axis (
    id uuid NOT NULL,
    start_range timestamp with time zone NOT NULL,
    end_range timestamp with time zone NOT NULL
);


ALTER TABLE metadata.time_coordinate_axis OWNER TO esgcet_admin;

--
-- TOC entry 258 (class 1259 OID 31764600)
-- Dependencies: 2413 6
-- Name: time_frequency; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE time_frequency (
    id uuid NOT NULL,
    name character varying(128) NOT NULL,
    description text,
    version integer DEFAULT 1
);


ALTER TABLE metadata.time_frequency OWNER TO esgcet_admin;

--
-- TOC entry 259 (class 1259 OID 31764607)
-- Dependencies: 6
-- Name: topic; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE topic (
    id uuid NOT NULL,
    version integer,
    name character varying(128) NOT NULL,
    taxonomy_id integer NOT NULL
);


ALTER TABLE metadata.topic OWNER TO esgcet_admin;

--
-- TOC entry 260 (class 1259 OID 31764610)
-- Dependencies: 6
-- Name: unit; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE unit (
    id uuid NOT NULL,
    version integer NOT NULL,
    symbol character varying(128) NOT NULL,
    name character varying(128),
    quantity character varying(255)
);


ALTER TABLE metadata.unit OWNER TO esgcet_admin;

--
-- TOC entry 261 (class 1259 OID 31764616)
-- Dependencies: 6
-- Name: user_agent_type_sequence; Type: SEQUENCE; Schema: metadata; Owner: esgcet_admin
--

CREATE SEQUENCE user_agent_type_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE metadata.user_agent_type_sequence OWNER TO esgcet_admin;

--
-- TOC entry 262 (class 1259 OID 31764618)
-- Dependencies: 6
-- Name: variable; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE variable (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    units_id uuid,
    dataset_version_id uuid
);


ALTER TABLE metadata.variable OWNER TO esgcet_admin;

--
-- TOC entry 263 (class 1259 OID 31764624)
-- Dependencies: 6
-- Name: variable_standard_name_xref; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE variable_standard_name_xref (
    variable_id uuid NOT NULL,
    standard_name_id uuid NOT NULL
);


ALTER TABLE metadata.variable_standard_name_xref OWNER TO esgcet_admin;

--
-- TOC entry 264 (class 1259 OID 31764627)
-- Dependencies: 6
-- Name: z_coordinate_axis; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE z_coordinate_axis (
    id uuid NOT NULL,
    version integer,
    z_positive_type_id integer NOT NULL
);


ALTER TABLE metadata.z_coordinate_axis OWNER TO esgcet_admin;

--
-- TOC entry 265 (class 1259 OID 31764630)
-- Dependencies: 6
-- Name: z_positive_type; Type: TABLE; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE z_positive_type (
    id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE metadata.z_positive_type OWNER TO esgcet_admin;

SET search_path = metrics, pg_catalog;

--
-- TOC entry 266 (class 1259 OID 31764636)
-- Dependencies: 7
-- Name: calendar; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE calendar (
    time_stamp timestamp with time zone NOT NULL,
    day_of_week smallint,
    day_of_month smallint,
    day_of_year smallint,
    day_name character varying(25),
    month smallint,
    month_name character varying(25),
    quarter smallint,
    year smallint,
    is_holiday boolean,
    is_weekday boolean
);


ALTER TABLE metrics.calendar OWNER TO esgcet_admin;

--
-- TOC entry 267 (class 1259 OID 31764639)
-- Dependencies: 7
-- Name: clickstream; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE clickstream (
    eske_session_id uuid NOT NULL,
    container_session_id text NOT NULL,
    date_created timestamp with time zone NOT NULL,
    user_id uuid,
    user_agent_id uuid,
    remote_address text NOT NULL,
    remote_port integer NOT NULL,
    protocol text NOT NULL,
    server_name text NOT NULL,
    server_port integer NOT NULL,
    request_uri text NOT NULL,
    query_string text,
    referrer text,
    request_time integer NOT NULL
);


ALTER TABLE metrics.clickstream OWNER TO esgcet_admin;

--
-- TOC entry 268 (class 1259 OID 31764645)
-- Dependencies: 2414 7
-- Name: datanode_harvest; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE datanode_harvest (
    version integer DEFAULT 1,
    name text NOT NULL,
    distinguished_name text NOT NULL,
    description text,
    metrics_service_endpoint text NOT NULL,
    last_metrics_harvest_date timestamp with time zone
);


ALTER TABLE metrics.datanode_harvest OWNER TO esgcet_admin;

--
-- TOC entry 269 (class 1259 OID 31764652)
-- Dependencies: 2415 7
-- Name: file_download; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE file_download (
    id uuid NOT NULL,
    user_id uuid,
    file_access_point_id uuid NOT NULL,
    user_agent_id uuid,
    date_started timestamp with time zone NOT NULL,
    date_completed timestamp with time zone,
    duration integer,
    success boolean,
    remote_address text,
    logical_file_size bigint NOT NULL,
    logical_file_id uuid,
    logical_file_name text,
    logical_file_lineage_id text,
    logical_file_version_id text,
    file_access_point_uri text,
    user_username text,
    user_openid text,
    user_email text,
    user_firstname text,
    user_lastname text,
    user_agent_name text,
    bytes_sent bigint DEFAULT 0 NOT NULL
);


ALTER TABLE metrics.file_download OWNER TO esgcet_admin;

--
-- TOC entry 270 (class 1259 OID 31764659)
-- Dependencies: 7
-- Name: numbers; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE numbers (
    number integer NOT NULL
);


ALTER TABLE metrics.numbers OWNER TO esgcet_admin;

--
-- TOC entry 271 (class 1259 OID 31764662)
-- Dependencies: 7
-- Name: operating_system_type; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE operating_system_type (
    id bigint NOT NULL,
    name character varying(256) NOT NULL
);


ALTER TABLE metrics.operating_system_type OWNER TO esgcet_admin;

--
-- TOC entry 272 (class 1259 OID 31764665)
-- Dependencies: 2416 2417 7
-- Name: user_agent; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE user_agent (
    id uuid NOT NULL,
    version bigint DEFAULT 1 NOT NULL,
    name character varying(1024) NOT NULL,
    ignore boolean DEFAULT false NOT NULL,
    user_agent_type_id bigint,
    operating_system_type_id bigint
);


ALTER TABLE metrics.user_agent OWNER TO esgcet_admin;

--
-- TOC entry 273 (class 1259 OID 31764673)
-- Dependencies: 7
-- Name: user_agent_type; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE user_agent_type (
    id bigint NOT NULL,
    name character varying(256) NOT NULL
);


ALTER TABLE metrics.user_agent_type OWNER TO esgcet_admin;

--
-- TOC entry 274 (class 1259 OID 31764676)
-- Dependencies: 2418 7
-- Name: user_login; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE user_login (
    users_id uuid NOT NULL,
    date_created timestamp with time zone DEFAULT now() NOT NULL,
    user_login_type_id integer NOT NULL
);


ALTER TABLE metrics.user_login OWNER TO esgcet_admin;

--
-- TOC entry 275 (class 1259 OID 31764680)
-- Dependencies: 7
-- Name: user_login_type; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE user_login_type (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE metrics.user_login_type OWNER TO esgcet_admin;

--
-- TOC entry 276 (class 1259 OID 31764683)
-- Dependencies: 7
-- Name: user_search; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE user_search (
    id uuid NOT NULL,
    users_id uuid NOT NULL,
    date_created timestamp with time zone NOT NULL,
    product character varying(255) NOT NULL,
    user_search_type_id integer NOT NULL
);


ALTER TABLE metrics.user_search OWNER TO esgcet_admin;

--
-- TOC entry 277 (class 1259 OID 31764686)
-- Dependencies: 7
-- Name: user_search_facet; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE user_search_facet (
    user_search_id uuid NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE metrics.user_search_facet OWNER TO esgcet_admin;

--
-- TOC entry 278 (class 1259 OID 31764692)
-- Dependencies: 7
-- Name: user_search_type; Type: TABLE; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE user_search_type (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255) NOT NULL
);


ALTER TABLE metrics.user_search_type OWNER TO esgcet_admin;

SET search_path = public, pg_catalog;

CREATE TABLE duplicate_order_count (
    count bigint
);


ALTER TABLE public.duplicate_order_count OWNER TO esgcet_admin;

SET search_path = security, pg_catalog;

--
-- TOC entry 282 (class 1259 OID 31764713)
-- Dependencies: 2419 2420 2421 9
-- Name: authorization_token; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE authorization_token (
    id uuid NOT NULL,
    version integer DEFAULT 1,
    uri text NOT NULL,
    date timestamp with time zone DEFAULT now() NOT NULL,
    expires timestamp with time zone DEFAULT now() NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE security.authorization_token OWNER TO esgcet_admin;

--
-- TOC entry 3244 (class 0 OID 0)
-- Dependencies: 282
-- Name: TABLE authorization_token; Type: COMMENT; Schema: security; Owner: esgcet_admin
--

COMMENT ON TABLE authorization_token IS 'Table to hold one-time access tokens for scripted download of files (for example, via wget).';


SET default_with_oids = true;

--
-- TOC entry 283 (class 1259 OID 31764722)
-- Dependencies: 2422 2423 9
-- Name: group; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE "group" (
    id uuid NOT NULL,
    name character varying(100) NOT NULL,
    description text NOT NULL,
    visible boolean DEFAULT true,
    automatic_approval boolean DEFAULT false,
    gateway_id uuid
);


ALTER TABLE security."group" OWNER TO esgcet_admin;

SET default_with_oids = false;

--
-- TOC entry 284 (class 1259 OID 31764730)
-- Dependencies: 2424 9
-- Name: group_data; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE group_data (
    id uuid NOT NULL,
    version integer DEFAULT 1,
    name character varying(100) NOT NULL,
    description text NOT NULL,
    group_data_type_id integer NOT NULL,
    value text
);


ALTER TABLE security.group_data OWNER TO esgcet_admin;

--
-- TOC entry 285 (class 1259 OID 31764737)
-- Dependencies: 9
-- Name: group_data_type; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE group_data_type (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE security.group_data_type OWNER TO esgcet_admin;

SET default_with_oids = true;

--
-- TOC entry 286 (class 1259 OID 31764740)
-- Dependencies: 9
-- Name: group_default_role; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE group_default_role (
    group_id uuid NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE security.group_default_role OWNER TO esgcet_admin;

--
-- TOC entry 287 (class 1259 OID 31764743)
-- Dependencies: 9
-- Name: group_group_data_xref; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE group_group_data_xref (
    group_id uuid NOT NULL,
    group_data_id uuid NOT NULL,
    required boolean NOT NULL
);


ALTER TABLE security.group_group_data_xref OWNER TO esgcet_admin;

--
-- TOC entry 288 (class 1259 OID 31764746)
-- Dependencies: 2425 9
-- Name: membership; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE membership (
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    group_id uuid NOT NULL,
    role_id integer NOT NULL,
    status_id integer NOT NULL,
    version integer DEFAULT 1
);


ALTER TABLE security.membership OWNER TO esgcet_admin;

--
-- TOC entry 289 (class 1259 OID 31764750)
-- Dependencies: 9
-- Name: user; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE "user" (
    id uuid NOT NULL,
    firstname character varying(100) NOT NULL,
    middlename character varying(100),
    lastname character varying(100) NOT NULL,
    email character varying(100) NOT NULL,
    username character varying(100),
    password character varying(100),
    dn character varying(300),
    openid character varying(200) NOT NULL,
    logintype character varying(100) NOT NULL,
    organization character varying(200),
    organization_type character varying(100),
    city character varying(100),
    state character varying(100),
    country character varying(100)
);


ALTER TABLE security."user" OWNER TO esgcet_admin;

--
-- TOC entry 290 (class 1259 OID 31764756)
-- Dependencies: 2366 9
-- Name: myproxy_user; Type: VIEW; Schema: security; Owner: esgcet_admin
--

CREATE VIEW myproxy_user AS
    SELECT "user".username, "user".password FROM "user" WHERE (("user".password IS NOT NULL) AND (("user".password)::text <> ''::text));


ALTER TABLE security.myproxy_user OWNER TO esgcet_admin;

SET default_with_oids = false;

--
-- TOC entry 291 (class 1259 OID 31764760)
-- Dependencies: 9
-- Name: operation; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE operation (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(200)
);


ALTER TABLE security.operation OWNER TO esgcet_admin;

SET default_with_oids = true;

--
-- TOC entry 292 (class 1259 OID 31764763)
-- Dependencies: 2426 9
-- Name: permission; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE permission (
    id uuid NOT NULL,
    resource_id uuid NOT NULL,
    operation_id integer NOT NULL,
    principal_id uuid NOT NULL,
    version integer DEFAULT 1
);


ALTER TABLE security.permission OWNER TO esgcet_admin;

SET default_with_oids = false;

--
-- TOC entry 293 (class 1259 OID 31764767)
-- Dependencies: 2427 2428 9
-- Name: principal; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE principal (
    id uuid NOT NULL,
    version integer DEFAULT 1,
    date_created timestamp with time zone DEFAULT now() NOT NULL
);


ALTER TABLE security.principal OWNER TO esgcet_admin;

SET default_with_oids = true;

--
-- TOC entry 294 (class 1259 OID 31764772)
-- Dependencies: 9
-- Name: role; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE role (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    description text NOT NULL
);


ALTER TABLE security.role OWNER TO esgcet_admin;

SET default_with_oids = false;

--
-- TOC entry 295 (class 1259 OID 31764778)
-- Dependencies: 2429 9
-- Name: role_operation_xref; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE role_operation_xref (
    id uuid NOT NULL,
    role_id integer NOT NULL,
    operation_id integer NOT NULL,
    version integer DEFAULT 1
);


ALTER TABLE security.role_operation_xref OWNER TO esgcet_admin;

SET default_with_oids = true;

--
-- TOC entry 296 (class 1259 OID 31764782)
-- Dependencies: 9
-- Name: status; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE status (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    description text
);


ALTER TABLE security.status OWNER TO esgcet_admin;

--
-- TOC entry 297 (class 1259 OID 31764788)
-- Dependencies: 2367 9
-- Name: user_attribute; Type: VIEW; Schema: security; Owner: esgcet_admin
--

CREATE VIEW user_attribute AS
    SELECT DISTINCT "user".username, "user".dn, "user".openid, ((('group_'::text || ("group".name)::text) || '_role_'::text) || (role.name)::text) AS attribute, 'sgf'::text AS gateway FROM "user" "user", principal principal, membership membership, "group" "group", role role, status status WHERE (((((("user".id = membership.user_id) AND ("group".id = membership.group_id)) AND (role.id = membership.role_id)) AND (membership.status_id = status.id)) AND ("user".id = principal.id)) AND (status.id = 3)) ORDER BY "user".username, "user".dn, ((('group_'::text || ("group".name)::text) || '_role_'::text) || (role.name)::text), "user".openid;


ALTER TABLE security.user_attribute OWNER TO esgcet_admin;

--
-- TOC entry 298 (class 1259 OID 31764793)
-- Dependencies: 2430 9
-- Name: user_data; Type: TABLE; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE user_data (
    id uuid NOT NULL,
    version integer DEFAULT 1,
    value text NOT NULL,
    group_data_id uuid NOT NULL,
    user_id uuid NOT NULL
);


ALTER TABLE security.user_data OWNER TO esgcet_admin;

SET search_path = workspace, pg_catalog;

--
-- TOC entry 299 (class 1259 OID 31764800)
-- Dependencies: 10
-- Name: charge_code; Type: TABLE; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE charge_code (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    description text
);


ALTER TABLE workspace.charge_code OWNER TO esgcet_admin;

--
-- TOC entry 300 (class 1259 OID 31764806)
-- Dependencies: 2431 2432 10
-- Name: data_transfer_item; Type: TABLE; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_transfer_item (
    id uuid NOT NULL,
    request_id uuid NOT NULL,
    source_id uuid NOT NULL,
    target_id uuid,
    error_id integer,
    version integer DEFAULT 1 NOT NULL,
    date_updated timestamp with time zone DEFAULT public.default_timestamp(),
    status_id integer,
    message text
);


ALTER TABLE workspace.data_transfer_item OWNER TO esgcet_admin;

--
-- TOC entry 301 (class 1259 OID 31764814)
-- Dependencies: 2433 2434 2435 2436 2437 10
-- Name: data_transfer_request; Type: TABLE; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_transfer_request (
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    dataset_id uuid NOT NULL,
    name character varying(255),
    request_number integer NOT NULL,
    total_size bigint DEFAULT 0,
    status_id integer,
    error_id integer,
    error_count integer DEFAULT 0,
    date_created timestamp with time zone DEFAULT public.default_timestamp() NOT NULL,
    date_completed timestamp with time zone,
    date_updated timestamp with time zone DEFAULT public.default_timestamp(),
    version integer DEFAULT 1 NOT NULL,
    subsystem_id character varying(255),
    data_access_capability_type_id uuid,
    message text
);


ALTER TABLE workspace.data_transfer_request OWNER TO esgcet_admin;

--
-- TOC entry 302 (class 1259 OID 31764825)
-- Dependencies: 10
-- Name: data_transfer_request_error; Type: TABLE; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_transfer_request_error (
    id integer NOT NULL,
    message character varying(255) NOT NULL,
    data_access_capability_type_id uuid NOT NULL
);


ALTER TABLE workspace.data_transfer_request_error OWNER TO esgcet_admin;

--
-- TOC entry 303 (class 1259 OID 31764828)
-- Dependencies: 10
-- Name: data_transfer_request_status; Type: TABLE; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE data_transfer_request_status (
    id integer NOT NULL,
    status character varying(255) NOT NULL
);


ALTER TABLE workspace.data_transfer_request_status OWNER TO esgcet_admin;

SET default_with_oids = false;

--
-- TOC entry 304 (class 1259 OID 31764831)
-- Dependencies: 2438 2439 10
-- Name: search_criteria; Type: TABLE; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE search_criteria (
    id uuid NOT NULL,
    version integer DEFAULT 1 NOT NULL,
    workspace_id uuid NOT NULL,
    name character varying(255),
    date_inserted timestamp with time zone DEFAULT now(),
    product character varying(255)
);


ALTER TABLE workspace.search_criteria OWNER TO esgcet_admin;

--
-- TOC entry 305 (class 1259 OID 31764839)
-- Dependencies: 2440 2441 10
-- Name: search_facet; Type: TABLE; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE search_facet (
    id uuid NOT NULL,
    name character varying(255) NOT NULL,
    search_criteria_id uuid,
    version integer DEFAULT 1 NOT NULL,
    number integer DEFAULT 0,
    value character varying(255)
);


ALTER TABLE workspace.search_facet OWNER TO esgcet_admin;

--
-- TOC entry 306 (class 1259 OID 31764847)
-- Dependencies: 2442 2443 10
-- Name: search_result; Type: TABLE; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE search_result (
    id uuid NOT NULL,
    version integer DEFAULT 1 NOT NULL,
    workspace_id uuid NOT NULL,
    rdf_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    date_inserted timestamp(0) without time zone DEFAULT now(),
    uri character varying(255),
    type character varying(255) NOT NULL
);


ALTER TABLE workspace.search_result OWNER TO esgcet_admin;

--
-- TOC entry 307 (class 1259 OID 31764855)
-- Dependencies: 2444 10
-- Name: workspace; Type: TABLE; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE TABLE workspace (
    id uuid NOT NULL,
    version integer DEFAULT 1 NOT NULL,
    principal_id uuid NOT NULL
);


ALTER TABLE workspace.workspace OWNER TO esgcet_admin;

SET search_path = metadata, pg_catalog;

--
-- TOC entry 2446 (class 2606 OID 34304251)
-- Dependencies: 165 165
-- Name: activity_rdf_identifier_key; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY activity
    ADD CONSTRAINT activity_rdf_identifier_key UNIQUE (rdf_identifier);


--
-- TOC entry 2499 (class 2606 OID 34304253)
-- Dependencies: 184 184
-- Name: data_access_application_pkey; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_access_application
    ADD CONSTRAINT data_access_application_pkey PRIMARY KEY (id);


--
-- TOC entry 2513 (class 2606 OID 34304255)
-- Dependencies: 189 189
-- Name: data_access_protocol_pkey; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_access_protocol
    ADD CONSTRAINT data_access_protocol_pkey PRIMARY KEY (id);


--
-- TOC entry 2515 (class 2606 OID 34304257)
-- Dependencies: 189 189
-- Name: data_access_protocol_protocol_key; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_access_protocol
    ADD CONSTRAINT data_access_protocol_protocol_key UNIQUE (protocol);


--
-- TOC entry 2527 (class 2606 OID 34304259)
-- Dependencies: 193 193
-- Name: datanode_distinguished_name_key; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY datanode
    ADD CONSTRAINT datanode_distinguished_name_key UNIQUE (distinguished_name);


--
-- TOC entry 2529 (class 2606 OID 34304261)
-- Dependencies: 193 193
-- Name: datanode_name_key; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY datanode
    ADD CONSTRAINT datanode_name_key UNIQUE (name);


--
-- TOC entry 2533 (class 2606 OID 34304263)
-- Dependencies: 194 194 194
-- Name: dataset_persistent_identifier_key; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset
    ADD CONSTRAINT dataset_persistent_identifier_key UNIQUE (persistent_identifier, authoritative_source);


--
-- TOC entry 2587 (class 2606 OID 34304265)
-- Dependencies: 207 207
-- Name: descriptive_metadata_pkey; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY descriptive_metadata
    ADD CONSTRAINT descriptive_metadata_pkey PRIMARY KEY (id);


--
-- TOC entry 2756 (class 2606 OID 34304267)
-- Dependencies: 250 250
-- Name: entity_type_pkey; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY resource_type
    ADD CONSTRAINT entity_type_pkey PRIMARY KEY (id);


--
-- TOC entry 2620 (class 2606 OID 34304269)
-- Dependencies: 215 215
-- Name: events_pkey; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- TOC entry 2625 (class 2606 OID 34304271)
-- Dependencies: 217 217
-- Name: federation_gateway_distinguished_name_key; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY federation_gateway
    ADD CONSTRAINT federation_gateway_distinguished_name_key UNIQUE (distinguished_name);


--
-- TOC entry 2627 (class 2606 OID 34304273)
-- Dependencies: 217 217
-- Name: federation_gateway_name_key; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY federation_gateway
    ADD CONSTRAINT federation_gateway_name_key UNIQUE (name);


--
-- TOC entry 2631 (class 2606 OID 34304275)
-- Dependencies: 218 218
-- Name: file_access_point_pkey; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY file_access_point
    ADD CONSTRAINT file_access_point_pkey PRIMARY KEY (id);


--
-- TOC entry 2633 (class 2606 OID 34304278)
-- Dependencies: 218 218
-- Name: file_access_point_unique_url; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY file_access_point
    ADD CONSTRAINT file_access_point_unique_url UNIQUE (full_access_url);


--
-- TOC entry 2464 (class 2606 OID 34304297)
-- Dependencies: 171 171
-- Name: id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY cadis_descriptive_metadata
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- TOC entry 2677 (class 2606 OID 34304299)
-- Dependencies: 228 228
-- Name: license_name_key; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY license
    ADD CONSTRAINT license_name_key UNIQUE (name);


--
-- TOC entry 2679 (class 2606 OID 34304301)
-- Dependencies: 228 228
-- Name: license_pkey; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY license
    ADD CONSTRAINT license_pkey PRIMARY KEY (id);


--
-- TOC entry 2731 (class 2606 OID 34304303)
-- Dependencies: 243 243
-- Name: persistent_identifier_unique; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY project
    ADD CONSTRAINT persistent_identifier_unique UNIQUE (persistent_identifier);


--
-- TOC entry 2749 (class 2606 OID 34304305)
-- Dependencies: 248 248
-- Name: pkey; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY resource
    ADD CONSTRAINT pkey PRIMARY KEY (id);


--
-- TOC entry 2450 (class 2606 OID 34304307)
-- Dependencies: 165 165
-- Name: pkey_activity_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY activity
    ADD CONSTRAINT pkey_activity_id PRIMARY KEY (id);


--
-- TOC entry 2456 (class 2606 OID 34304309)
-- Dependencies: 166 166 166 166
-- Name: pkey_activity_link; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY activity_link
    ADD CONSTRAINT pkey_activity_link PRIMARY KEY (activity_id, text, uri);


--
-- TOC entry 2458 (class 2606 OID 34304311)
-- Dependencies: 167 167
-- Name: pkey_activity_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY activity_type
    ADD CONSTRAINT pkey_activity_type_id PRIMARY KEY (id);


--
-- TOC entry 2460 (class 2606 OID 34304313)
-- Dependencies: 168 168 168
-- Name: pkey_award; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY award
    ADD CONSTRAINT pkey_award PRIMARY KEY (project_id, award_number);


--
-- TOC entry 2462 (class 2606 OID 34304315)
-- Dependencies: 169 169
-- Name: pkey_broadcast_message; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY broadcast_message
    ADD CONSTRAINT pkey_broadcast_message PRIMARY KEY (id);


--
-- TOC entry 2468 (class 2606 OID 34304317)
-- Dependencies: 172 172 172
-- Name: pkey_cadis_descriptive_metadata_cadis_resolution_type_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY cadis_descriptive_metadata_cadis_resolution_type_xref
    ADD CONSTRAINT pkey_cadis_descriptive_metadata_cadis_resolution_type_xref PRIMARY KEY (cadis_descriptive_metadata_id, cadis_resolution_type_id);


--
-- TOC entry 2470 (class 2606 OID 34304319)
-- Dependencies: 173 173
-- Name: pkey_cadis_project; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY cadis_project
    ADD CONSTRAINT pkey_cadis_project PRIMARY KEY (id);


--
-- TOC entry 2472 (class 2606 OID 34304321)
-- Dependencies: 174 174 174
-- Name: pkey_cadis_project_project_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY cadis_project_project_xref
    ADD CONSTRAINT pkey_cadis_project_project_xref PRIMARY KEY (cadis_project_id, project_id);


--
-- TOC entry 2474 (class 2606 OID 34304323)
-- Dependencies: 175 175 175 175
-- Name: pkey_cadis_project_typed_contact; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY cadis_project_typed_contact
    ADD CONSTRAINT pkey_cadis_project_typed_contact PRIMARY KEY (cadis_project_id, contact_id, contact_type_id);


--
-- TOC entry 2476 (class 2606 OID 34304325)
-- Dependencies: 176 176
-- Name: pkey_cadis_resolution_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY cadis_resolution_type
    ADD CONSTRAINT pkey_cadis_resolution_type_id PRIMARY KEY (id);


--
-- TOC entry 2479 (class 2606 OID 34304327)
-- Dependencies: 177 177
-- Name: pkey_campaign; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY campaign
    ADD CONSTRAINT pkey_campaign PRIMARY KEY (id);


--
-- TOC entry 2482 (class 2606 OID 34304329)
-- Dependencies: 178 178 178 178
-- Name: pkey_checksum; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY checksum
    ADD CONSTRAINT pkey_checksum PRIMARY KEY (logical_file_id, value, algorithm);


--
-- TOC entry 2486 (class 2606 OID 34304331)
-- Dependencies: 180 180
-- Name: pkey_contact_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT pkey_contact_id PRIMARY KEY (id);


--
-- TOC entry 2490 (class 2606 OID 34304333)
-- Dependencies: 181 181
-- Name: pkey_contact_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY contact_type
    ADD CONSTRAINT pkey_contact_type_id PRIMARY KEY (id);


--
-- TOC entry 2495 (class 2606 OID 34304335)
-- Dependencies: 182 182
-- Name: pkey_coordinate_axis_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY coordinate_axis
    ADD CONSTRAINT pkey_coordinate_axis_id PRIMARY KEY (id);


--
-- TOC entry 2497 (class 2606 OID 34304337)
-- Dependencies: 183 183
-- Name: pkey_coordinate_type; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY coordinate_type
    ADD CONSTRAINT pkey_coordinate_type PRIMARY KEY (id);


--
-- TOC entry 2502 (class 2606 OID 34304339)
-- Dependencies: 186 186
-- Name: pkey_data_access_capability; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_access_capability
    ADD CONSTRAINT pkey_data_access_capability PRIMARY KEY (id);


--
-- TOC entry 2509 (class 2606 OID 34304341)
-- Dependencies: 188 188
-- Name: pkey_data_access_capability_type; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_access_capability_type
    ADD CONSTRAINT pkey_data_access_capability_type PRIMARY KEY (id);


--
-- TOC entry 2517 (class 2606 OID 34304343)
-- Dependencies: 190 190
-- Name: pkey_data_format_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_format
    ADD CONSTRAINT pkey_data_format_id PRIMARY KEY (id);


--
-- TOC entry 2521 (class 2606 OID 34304345)
-- Dependencies: 191 191
-- Name: pkey_data_product_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_product_type
    ADD CONSTRAINT pkey_data_product_type_id PRIMARY KEY (id);


--
-- TOC entry 2525 (class 2606 OID 34304347)
-- Dependencies: 192 192
-- Name: pkey_data_type; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_type
    ADD CONSTRAINT pkey_data_type PRIMARY KEY (id);


--
-- TOC entry 2531 (class 2606 OID 34304349)
-- Dependencies: 193 193
-- Name: pkey_datanode; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY datanode
    ADD CONSTRAINT pkey_datanode PRIMARY KEY (id);


--
-- TOC entry 2542 (class 2606 OID 34304351)
-- Dependencies: 195 195
-- Name: pkey_dataset_access_point; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_access_point
    ADD CONSTRAINT pkey_dataset_access_point PRIMARY KEY (id);


--
-- TOC entry 2546 (class 2606 OID 34304353)
-- Dependencies: 196 196 196
-- Name: pkey_dataset_activity_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_activity_xref
    ADD CONSTRAINT pkey_dataset_activity_xref PRIMARY KEY (dataset_id, activity_id);


--
-- TOC entry 2551 (class 2606 OID 34304355)
-- Dependencies: 197 197
-- Name: pkey_dataset_citation_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_citation
    ADD CONSTRAINT pkey_dataset_citation_id PRIMARY KEY (id);


--
-- TOC entry 2556 (class 2606 OID 34304357)
-- Dependencies: 198 198 198 198
-- Name: pkey_dataset_contact_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_contact_xref
    ADD CONSTRAINT pkey_dataset_contact_xref PRIMARY KEY (contact_id, contact_type_id, dataset_id);


--
-- TOC entry 2560 (class 2606 OID 34304359)
-- Dependencies: 199 199 199
-- Name: pkey_dataset_data_format_xref_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_data_format_xref
    ADD CONSTRAINT pkey_dataset_data_format_xref_id PRIMARY KEY (dataset_id, data_format_id);


--
-- TOC entry 2539 (class 2606 OID 34304361)
-- Dependencies: 194 194
-- Name: pkey_dataset_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset
    ADD CONSTRAINT pkey_dataset_id PRIMARY KEY (id);


--
-- TOC entry 2562 (class 2606 OID 34304363)
-- Dependencies: 200 200
-- Name: pkey_dataset_progress_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_progress
    ADD CONSTRAINT pkey_dataset_progress_id PRIMARY KEY (id);


--
-- TOC entry 2565 (class 2606 OID 34304365)
-- Dependencies: 201 201 201 201
-- Name: pkey_dataset_restriction; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_restriction
    ADD CONSTRAINT pkey_dataset_restriction PRIMARY KEY (id, idx_index, restriction);


--
-- TOC entry 2569 (class 2606 OID 34304367)
-- Dependencies: 202 202 202
-- Name: pkey_dataset_topic_xref_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_topic_xref
    ADD CONSTRAINT pkey_dataset_topic_xref_id PRIMARY KEY (dataset_id, topic_id);


--
-- TOC entry 2574 (class 2606 OID 34304369)
-- Dependencies: 203 203 203 203
-- Name: pkey_dataset_typed_contact_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_typed_contact
    ADD CONSTRAINT pkey_dataset_typed_contact_id PRIMARY KEY (dataset_id, contact_id, contact_type_id);


--
-- TOC entry 2579 (class 2606 OID 34304371)
-- Dependencies: 204 204
-- Name: pkey_dataset_version; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_version
    ADD CONSTRAINT pkey_dataset_version PRIMARY KEY (id);


--
-- TOC entry 2585 (class 2606 OID 34304373)
-- Dependencies: 206 206 206
-- Name: pkey_dataset_version_variable_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_version_variable_xref
    ADD CONSTRAINT pkey_dataset_version_variable_xref PRIMARY KEY (dataset_version_id, variable_id);


--
-- TOC entry 2595 (class 2606 OID 34304375)
-- Dependencies: 208 208 208
-- Name: pkey_descriptive_metadata_data_type_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY descriptive_metadata_data_type_xref
    ADD CONSTRAINT pkey_descriptive_metadata_data_type_xref PRIMARY KEY (descriptive_metadata_id, data_type_id);


--
-- TOC entry 2599 (class 2606 OID 34304377)
-- Dependencies: 209 209 209
-- Name: pkey_descriptive_metadata_instrument_type_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY descriptive_metadata_instrument_type_xref
    ADD CONSTRAINT pkey_descriptive_metadata_instrument_type_xref PRIMARY KEY (descriptive_metadata_id, instrument_type_id);


--
-- TOC entry 2603 (class 2606 OID 34304379)
-- Dependencies: 210 210 210 210
-- Name: pkey_descriptive_metadata_link; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY descriptive_metadata_link
    ADD CONSTRAINT pkey_descriptive_metadata_link PRIMARY KEY (descriptive_metadata_id, text, uri);


--
-- TOC entry 2607 (class 2606 OID 34304381)
-- Dependencies: 211 211 211
-- Name: pkey_descriptive_metadata_location_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY descriptive_metadata_location_xref
    ADD CONSTRAINT pkey_descriptive_metadata_location_xref PRIMARY KEY (descriptive_metadata_id, location_id);


--
-- TOC entry 2611 (class 2606 OID 34304383)
-- Dependencies: 212 212 212
-- Name: pkey_descriptive_metadata_platform_type_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY descriptive_metadata_platform_type_xref
    ADD CONSTRAINT pkey_descriptive_metadata_platform_type_xref PRIMARY KEY (descriptive_metadata_id, platform_type_id);


--
-- TOC entry 2615 (class 2606 OID 34304385)
-- Dependencies: 213 213 213
-- Name: pkey_descriptive_metadata_time_frequency_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY descriptive_metadata_time_frequency_xref
    ADD CONSTRAINT pkey_descriptive_metadata_time_frequency_xref PRIMARY KEY (descriptive_metadata_id, time_frequency_id);


--
-- TOC entry 2618 (class 2606 OID 34304387)
-- Dependencies: 214 214
-- Name: pkey_ensemble; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY ensemble
    ADD CONSTRAINT pkey_ensemble PRIMARY KEY (id);


--
-- TOC entry 2623 (class 2606 OID 34304389)
-- Dependencies: 216 216
-- Name: pkey_experiment; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY experiment
    ADD CONSTRAINT pkey_experiment PRIMARY KEY (id);


--
-- TOC entry 2629 (class 2606 OID 34304391)
-- Dependencies: 217 217
-- Name: pkey_federation_gateway_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY federation_gateway
    ADD CONSTRAINT pkey_federation_gateway_id PRIMARY KEY (id);


--
-- TOC entry 2638 (class 2606 OID 34304393)
-- Dependencies: 219 219
-- Name: pkey_forcing; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY forcing
    ADD CONSTRAINT pkey_forcing PRIMARY KEY (id);


--
-- TOC entry 2642 (class 2606 OID 34304395)
-- Dependencies: 220 220
-- Name: pkey_forcing_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY forcing_type
    ADD CONSTRAINT pkey_forcing_type_id PRIMARY KEY (id);


--
-- TOC entry 2647 (class 2606 OID 34304397)
-- Dependencies: 221 221
-- Name: pkey_gateway_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY gateway
    ADD CONSTRAINT pkey_gateway_id PRIMARY KEY (id);


--
-- TOC entry 2660 (class 2606 OID 34304399)
-- Dependencies: 222 222
-- Name: pkey_gateway_specific_metadata; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY gateway_specific_metadata
    ADD CONSTRAINT pkey_gateway_specific_metadata PRIMARY KEY (id);


--
-- TOC entry 2664 (class 2606 OID 34304401)
-- Dependencies: 223 223
-- Name: pkey_geophysical_properties; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY geophysical_properties
    ADD CONSTRAINT pkey_geophysical_properties PRIMARY KEY (id);


--
-- TOC entry 2667 (class 2606 OID 34304403)
-- Dependencies: 224 224
-- Name: pkey_geospatial_coordinate_axis_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY geospatial_coordinate_axis
    ADD CONSTRAINT pkey_geospatial_coordinate_axis_id PRIMARY KEY (id);


--
-- TOC entry 2669 (class 2606 OID 34304405)
-- Dependencies: 225 225
-- Name: pkey_grid_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY grid
    ADD CONSTRAINT pkey_grid_id PRIMARY KEY (id);


--
-- TOC entry 2673 (class 2606 OID 34304407)
-- Dependencies: 226 226
-- Name: pkey_institution; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY institution
    ADD CONSTRAINT pkey_institution PRIMARY KEY (id);


--
-- TOC entry 2675 (class 2606 OID 34304409)
-- Dependencies: 227 227
-- Name: pkey_instrument_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY instrument_type
    ADD CONSTRAINT pkey_instrument_type_id PRIMARY KEY (id);


--
-- TOC entry 2681 (class 2606 OID 34304411)
-- Dependencies: 229 229
-- Name: pkey_link_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY link_type
    ADD CONSTRAINT pkey_link_type_id PRIMARY KEY (id);


--
-- TOC entry 2684 (class 2606 OID 34304413)
-- Dependencies: 230 230
-- Name: pkey_location_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY location
    ADD CONSTRAINT pkey_location_id PRIMARY KEY (id);


--
-- TOC entry 2692 (class 2606 OID 34304415)
-- Dependencies: 232 232 232
-- Name: pkey_logical_file_dataset_version_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY logical_file_dataset_version_xref
    ADD CONSTRAINT pkey_logical_file_dataset_version_xref PRIMARY KEY (logical_file_id, dataset_version_id);


--
-- TOC entry 2688 (class 2606 OID 34304417)
-- Dependencies: 231 231
-- Name: pkey_logical_file_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY logical_file
    ADD CONSTRAINT pkey_logical_file_id PRIMARY KEY (id);


--
-- TOC entry 2696 (class 2606 OID 34304419)
-- Dependencies: 233 233 233
-- Name: pkey_logical_file_variable_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY logical_file_variable_xref
    ADD CONSTRAINT pkey_logical_file_variable_xref PRIMARY KEY (logical_file_id, variable_id);


--
-- TOC entry 2698 (class 2606 OID 34304421)
-- Dependencies: 234 234
-- Name: pkey_metadata_profile; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY metadata_profile
    ADD CONSTRAINT pkey_metadata_profile PRIMARY KEY (id);


--
-- TOC entry 2702 (class 2606 OID 34304423)
-- Dependencies: 235 235
-- Name: pkey_model; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY model
    ADD CONSTRAINT pkey_model PRIMARY KEY (id);


--
-- TOC entry 2705 (class 2606 OID 34304425)
-- Dependencies: 236 236
-- Name: pkey_model_component; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY model_component
    ADD CONSTRAINT pkey_model_component PRIMARY KEY (id);


--
-- TOC entry 2707 (class 2606 OID 34304427)
-- Dependencies: 237 237
-- Name: pkey_model_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY model_type
    ADD CONSTRAINT pkey_model_type_id PRIMARY KEY (id);


--
-- TOC entry 2715 (class 2606 OID 34304429)
-- Dependencies: 239 239 239 239
-- Name: pkey_persistent_identifier; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY persistent_identifier
    ADD CONSTRAINT pkey_persistent_identifier PRIMARY KEY (resource_id, type_id, identifier);


--
-- TOC entry 2719 (class 2606 OID 34304431)
-- Dependencies: 240 240
-- Name: pkey_persistent_indentifier_type; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY persistent_indentifier_type
    ADD CONSTRAINT pkey_persistent_indentifier_type PRIMARY KEY (id);


--
-- TOC entry 2723 (class 2606 OID 34304433)
-- Dependencies: 241 241
-- Name: pkey_physical_domain_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY physical_domain
    ADD CONSTRAINT pkey_physical_domain_id PRIMARY KEY (id);


--
-- TOC entry 2727 (class 2606 OID 34304435)
-- Dependencies: 242 242
-- Name: pkey_platform_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY platform_type
    ADD CONSTRAINT pkey_platform_type_id PRIMARY KEY (id);


--
-- TOC entry 2733 (class 2606 OID 34304437)
-- Dependencies: 243 243
-- Name: pkey_project_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY project
    ADD CONSTRAINT pkey_project_id PRIMARY KEY (id);


--
-- TOC entry 2735 (class 2606 OID 34304439)
-- Dependencies: 244 244
-- Name: pkey_published_state_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY published_state
    ADD CONSTRAINT pkey_published_state_id PRIMARY KEY (id);


--
-- TOC entry 2737 (class 2606 OID 34304441)
-- Dependencies: 245 245
-- Name: pkey_publishing_operation_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY publishing_operation
    ADD CONSTRAINT pkey_publishing_operation_id PRIMARY KEY (id);


--
-- TOC entry 2739 (class 2606 OID 34304443)
-- Dependencies: 246 246
-- Name: pkey_replica; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY replica
    ADD CONSTRAINT pkey_replica PRIMARY KEY (id);


--
-- TOC entry 2743 (class 2606 OID 34304445)
-- Dependencies: 247 247
-- Name: pkey_resolution_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY resolution_type
    ADD CONSTRAINT pkey_resolution_type_id PRIMARY KEY (id);


--
-- TOC entry 2754 (class 2606 OID 34304447)
-- Dependencies: 249 249 249
-- Name: pkey_resource_tag_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY resource_tag_xref
    ADD CONSTRAINT pkey_resource_tag_xref PRIMARY KEY (tag_ref, resource_ref);


--
-- TOC entry 2759 (class 2606 OID 34304449)
-- Dependencies: 251 251
-- Name: pkey_software_properties; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY software_properties
    ADD CONSTRAINT pkey_software_properties PRIMARY KEY (id);


--
-- TOC entry 2763 (class 2606 OID 34304451)
-- Dependencies: 252 252
-- Name: pkey_standard_name_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY standard_name
    ADD CONSTRAINT pkey_standard_name_id PRIMARY KEY (id);


--
-- TOC entry 2767 (class 2606 OID 34304453)
-- Dependencies: 253 253
-- Name: pkey_standard_name_type; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY standard_name_type
    ADD CONSTRAINT pkey_standard_name_type PRIMARY KEY (id);


--
-- TOC entry 2771 (class 2606 OID 34304455)
-- Dependencies: 254 254
-- Name: pkey_storage_type; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY storage_type
    ADD CONSTRAINT pkey_storage_type PRIMARY KEY (id);


--
-- TOC entry 2773 (class 2606 OID 34304457)
-- Dependencies: 255 255
-- Name: pkey_tag; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT pkey_tag PRIMARY KEY (id);


--
-- TOC entry 2775 (class 2606 OID 34304459)
-- Dependencies: 256 256
-- Name: pkey_taxonomy; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY taxonomy
    ADD CONSTRAINT pkey_taxonomy PRIMARY KEY (id);


--
-- TOC entry 2778 (class 2606 OID 34304461)
-- Dependencies: 257 257
-- Name: pkey_time_coordinate_axis_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY time_coordinate_axis
    ADD CONSTRAINT pkey_time_coordinate_axis_id PRIMARY KEY (id);


--
-- TOC entry 2780 (class 2606 OID 34304463)
-- Dependencies: 258 258
-- Name: pkey_time_frequency_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY time_frequency
    ADD CONSTRAINT pkey_time_frequency_id PRIMARY KEY (id);


--
-- TOC entry 2785 (class 2606 OID 34304465)
-- Dependencies: 259 259
-- Name: pkey_topic_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY topic
    ADD CONSTRAINT pkey_topic_id PRIMARY KEY (id);


--
-- TOC entry 2788 (class 2606 OID 34304467)
-- Dependencies: 260 260
-- Name: pkey_unit_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY unit
    ADD CONSTRAINT pkey_unit_id PRIMARY KEY (id);


--
-- TOC entry 2793 (class 2606 OID 34304469)
-- Dependencies: 262 262
-- Name: pkey_variable_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY variable
    ADD CONSTRAINT pkey_variable_id PRIMARY KEY (id);


--
-- TOC entry 2797 (class 2606 OID 34304471)
-- Dependencies: 263 263 263
-- Name: pkey_variable_standard_name_xref; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY variable_standard_name_xref
    ADD CONSTRAINT pkey_variable_standard_name_xref PRIMARY KEY (variable_id, standard_name_id);


--
-- TOC entry 2801 (class 2606 OID 34304473)
-- Dependencies: 264 264
-- Name: pkey_z_coordinate_axis_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY z_coordinate_axis
    ADD CONSTRAINT pkey_z_coordinate_axis_id PRIMARY KEY (id);


--
-- TOC entry 2803 (class 2606 OID 34304475)
-- Dependencies: 265 265
-- Name: pkey_z_positive_type_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY z_positive_type
    ADD CONSTRAINT pkey_z_positive_type_id PRIMARY KEY (id);


--
-- TOC entry 2644 (class 2606 OID 34304477)
-- Dependencies: 220 220
-- Name: uni_forcing_description; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY forcing_type
    ADD CONSTRAINT uni_forcing_description UNIQUE (description);


--
-- TOC entry 2745 (class 2606 OID 34304479)
-- Dependencies: 247 247
-- Name: uni_resolution_type_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY resolution_type
    ADD CONSTRAINT uni_resolution_type_name UNIQUE (name);


--
-- TOC entry 2452 (class 2606 OID 34304481)
-- Dependencies: 165 165
-- Name: unq_activity_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY activity
    ADD CONSTRAINT unq_activity_name UNIQUE (name);


--
-- TOC entry 2488 (class 2606 OID 34304483)
-- Dependencies: 180 180 180 180 180
-- Name: unq_contact; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT unq_contact UNIQUE (first_name, last_name, middle_name, email);


--
-- TOC entry 2504 (class 2606 OID 34304485)
-- Dependencies: 186 186 186 186
-- Name: unq_data_access_capability; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_access_capability
    ADD CONSTRAINT unq_data_access_capability UNIQUE (name, capability_type_id, base_uri);


--
-- TOC entry 2511 (class 2606 OID 34304487)
-- Dependencies: 188 188
-- Name: unq_data_access_capability_type_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_access_capability_type
    ADD CONSTRAINT unq_data_access_capability_type_name UNIQUE (name);


--
-- TOC entry 2519 (class 2606 OID 34304489)
-- Dependencies: 190 190
-- Name: unq_data_format_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_format
    ADD CONSTRAINT unq_data_format_name UNIQUE (name);


--
-- TOC entry 2523 (class 2606 OID 34304491)
-- Dependencies: 191 191
-- Name: unq_data_product_type_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_product_type
    ADD CONSTRAINT unq_data_product_type_name UNIQUE (name);


--
-- TOC entry 2581 (class 2606 OID 34304493)
-- Dependencies: 204 204 204
-- Name: unq_dataset_version_datasetid_versionid; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY dataset_version
    ADD CONSTRAINT unq_dataset_version_datasetid_versionid UNIQUE (dataset_id, version_id);


--
-- TOC entry 2640 (class 2606 OID 34304495)
-- Dependencies: 219 219
-- Name: unq_forcing_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY forcing
    ADD CONSTRAINT unq_forcing_name UNIQUE (name);


--
-- TOC entry 2649 (class 2606 OID 34304497)
-- Dependencies: 221 221
-- Name: unq_gateway_base_secure_url; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY gateway
    ADD CONSTRAINT unq_gateway_base_secure_url UNIQUE (base_secure_url);


--
-- TOC entry 2651 (class 2606 OID 34304499)
-- Dependencies: 221 221
-- Name: unq_gateway_base_url; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY gateway
    ADD CONSTRAINT unq_gateway_base_url UNIQUE (base_url);


--
-- TOC entry 2653 (class 2606 OID 34304501)
-- Dependencies: 221 221
-- Name: unq_gateway_dn; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY gateway
    ADD CONSTRAINT unq_gateway_dn UNIQUE (identity);


--
-- TOC entry 2655 (class 2606 OID 34304503)
-- Dependencies: 221 221
-- Name: unq_gateway_idp_endpoint; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY gateway
    ADD CONSTRAINT unq_gateway_idp_endpoint UNIQUE (idp_endpoint);


--
-- TOC entry 2657 (class 2606 OID 34304505)
-- Dependencies: 221 221
-- Name: unq_gateway_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY gateway
    ADD CONSTRAINT unq_gateway_name UNIQUE (name);


--
-- TOC entry 2671 (class 2606 OID 34304507)
-- Dependencies: 225 225
-- Name: unq_grid_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY grid
    ADD CONSTRAINT unq_grid_name UNIQUE (name);


--
-- TOC entry 2700 (class 2606 OID 34304509)
-- Dependencies: 234 234
-- Name: unq_metadata_profile; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY metadata_profile
    ADD CONSTRAINT unq_metadata_profile UNIQUE (profile_name);


--
-- TOC entry 2709 (class 2606 OID 34304511)
-- Dependencies: 237 237
-- Name: unq_model_type_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY model_type
    ADD CONSTRAINT unq_model_type_name UNIQUE (name);


--
-- TOC entry 2717 (class 2606 OID 34304513)
-- Dependencies: 239 239 239
-- Name: unq_persistent_identifier; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY persistent_identifier
    ADD CONSTRAINT unq_persistent_identifier UNIQUE (identifier, type_id);


--
-- TOC entry 2721 (class 2606 OID 34304515)
-- Dependencies: 240 240
-- Name: unq_persistent_indentifier; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY persistent_indentifier_type
    ADD CONSTRAINT unq_persistent_indentifier UNIQUE (name);


--
-- TOC entry 2725 (class 2606 OID 34304517)
-- Dependencies: 241 241
-- Name: unq_physical_domain_id; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY physical_domain
    ADD CONSTRAINT unq_physical_domain_id UNIQUE (name);


--
-- TOC entry 2741 (class 2606 OID 34304519)
-- Dependencies: 246 246
-- Name: unq_replica_source_catalog_uri; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY replica
    ADD CONSTRAINT unq_replica_source_catalog_uri UNIQUE (source_catalog_uri);


--
-- TOC entry 2765 (class 2606 OID 34304521)
-- Dependencies: 252 252 252
-- Name: unq_standard_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY standard_name
    ADD CONSTRAINT unq_standard_name UNIQUE (name, type_id);


--
-- TOC entry 2769 (class 2606 OID 34304523)
-- Dependencies: 253 253
-- Name: unq_standard_name_description; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY standard_name_type
    ADD CONSTRAINT unq_standard_name_description UNIQUE (description);


--
-- TOC entry 2782 (class 2606 OID 34304525)
-- Dependencies: 258 258
-- Name: unq_time_frequency_name; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY time_frequency
    ADD CONSTRAINT unq_time_frequency_name UNIQUE (name);


--
-- TOC entry 2790 (class 2606 OID 34304527)
-- Dependencies: 260 260
-- Name: unq_unit_symbol; Type: CONSTRAINT; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY unit
    ADD CONSTRAINT unq_unit_symbol UNIQUE (symbol);


SET search_path = metrics, pg_catalog;

--
-- TOC entry 2805 (class 2606 OID 34304529)
-- Dependencies: 266 266
-- Name: calendar_pkey; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY calendar
    ADD CONSTRAINT calendar_pkey PRIMARY KEY (time_stamp);


--
-- TOC entry 2809 (class 2606 OID 34304531)
-- Dependencies: 268 268
-- Name: datanode_harvest_name_key; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY datanode_harvest
    ADD CONSTRAINT datanode_harvest_name_key UNIQUE (name);


--
-- TOC entry 2811 (class 2606 OID 34304533)
-- Dependencies: 268 268
-- Name: pkey_datanode_harvest; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY datanode_harvest
    ADD CONSTRAINT pkey_datanode_harvest PRIMARY KEY (distinguished_name);


--
-- TOC entry 2816 (class 2606 OID 34304535)
-- Dependencies: 269 269
-- Name: pkey_file_download; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY file_download
    ADD CONSTRAINT pkey_file_download PRIMARY KEY (id);


--
-- TOC entry 2818 (class 2606 OID 34304537)
-- Dependencies: 270 270
-- Name: pkey_numbers; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY numbers
    ADD CONSTRAINT pkey_numbers PRIMARY KEY (number);


--
-- TOC entry 2820 (class 2606 OID 34304539)
-- Dependencies: 271 271
-- Name: pkey_operating_system_type; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY operating_system_type
    ADD CONSTRAINT pkey_operating_system_type PRIMARY KEY (id);


--
-- TOC entry 2827 (class 2606 OID 34304541)
-- Dependencies: 272 272
-- Name: pkey_user_agent; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_agent
    ADD CONSTRAINT pkey_user_agent PRIMARY KEY (id);


--
-- TOC entry 2831 (class 2606 OID 34304543)
-- Dependencies: 273 273
-- Name: pkey_user_agent_type; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_agent_type
    ADD CONSTRAINT pkey_user_agent_type PRIMARY KEY (id);


--
-- TOC entry 2837 (class 2606 OID 34304545)
-- Dependencies: 274 274 274
-- Name: pkey_user_login; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_login
    ADD CONSTRAINT pkey_user_login PRIMARY KEY (users_id, date_created);


--
-- TOC entry 2839 (class 2606 OID 34304547)
-- Dependencies: 275 275
-- Name: pkey_user_login_type; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_login_type
    ADD CONSTRAINT pkey_user_login_type PRIMARY KEY (id);


--
-- TOC entry 2842 (class 2606 OID 34304549)
-- Dependencies: 276 276
-- Name: pkey_user_search; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_search
    ADD CONSTRAINT pkey_user_search PRIMARY KEY (id);


--
-- TOC entry 2845 (class 2606 OID 34304551)
-- Dependencies: 277 277 277 277
-- Name: pkey_user_search_facet; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_search_facet
    ADD CONSTRAINT pkey_user_search_facet PRIMARY KEY (user_search_id, name, value);


--
-- TOC entry 2847 (class 2606 OID 34304553)
-- Dependencies: 278 278
-- Name: pkey_user_search_type; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_search_type
    ADD CONSTRAINT pkey_user_search_type PRIMARY KEY (id);


--
-- TOC entry 2822 (class 2606 OID 34304555)
-- Dependencies: 271 271
-- Name: unq_operating_system_type; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY operating_system_type
    ADD CONSTRAINT unq_operating_system_type UNIQUE (name);


--
-- TOC entry 2829 (class 2606 OID 34304557)
-- Dependencies: 272 272
-- Name: unq_user_agent_name; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_agent
    ADD CONSTRAINT unq_user_agent_name UNIQUE (name);


--
-- TOC entry 2833 (class 2606 OID 34304559)
-- Dependencies: 273 273
-- Name: unq_user_agent_type_name; Type: CONSTRAINT; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_agent_type
    ADD CONSTRAINT unq_user_agent_type_name UNIQUE (name);


SET search_path = security, pg_catalog;

--
-- TOC entry 2862 (class 2606 OID 34304567)
-- Dependencies: 284 284
-- Name: pkey_group_data_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY group_data
    ADD CONSTRAINT pkey_group_data_id PRIMARY KEY (id);


--
-- TOC entry 2866 (class 2606 OID 34304569)
-- Dependencies: 285 285
-- Name: pkey_group_data_type_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY group_data_type
    ADD CONSTRAINT pkey_group_data_type_id PRIMARY KEY (id);


--
-- TOC entry 2872 (class 2606 OID 34304571)
-- Dependencies: 286 286 286
-- Name: pkey_group_default_role_group_id_role_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY group_default_role
    ADD CONSTRAINT pkey_group_default_role_group_id_role_id PRIMARY KEY (group_id, role_id);


--
-- TOC entry 2876 (class 2606 OID 34304573)
-- Dependencies: 287 287 287
-- Name: pkey_group_group_data_xref_group_id_group_data_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY group_group_data_xref
    ADD CONSTRAINT pkey_group_group_data_xref_group_id_group_data_id PRIMARY KEY (group_id, group_data_id);


--
-- TOC entry 2857 (class 2606 OID 34304575)
-- Dependencies: 283 283
-- Name: pkey_group_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY "group"
    ADD CONSTRAINT pkey_group_id PRIMARY KEY (id);


--
-- TOC entry 2854 (class 2606 OID 34304577)
-- Dependencies: 282 282
-- Name: pkey_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY authorization_token
    ADD CONSTRAINT pkey_id PRIMARY KEY (id);


--
-- TOC entry 2882 (class 2606 OID 34304579)
-- Dependencies: 288 288
-- Name: pkey_membership_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY membership
    ADD CONSTRAINT pkey_membership_id PRIMARY KEY (id);


--
-- TOC entry 2891 (class 2606 OID 34304581)
-- Dependencies: 291 291
-- Name: pkey_operation_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY operation
    ADD CONSTRAINT pkey_operation_id PRIMARY KEY (id);


--
-- TOC entry 2898 (class 2606 OID 34304583)
-- Dependencies: 292 292
-- Name: pkey_permission_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT pkey_permission_id PRIMARY KEY (id);


--
-- TOC entry 2902 (class 2606 OID 34304585)
-- Dependencies: 293 293
-- Name: pkey_principal_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY principal
    ADD CONSTRAINT pkey_principal_id PRIMARY KEY (id);


--
-- TOC entry 2904 (class 2606 OID 34304587)
-- Dependencies: 294 294
-- Name: pkey_role_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY role
    ADD CONSTRAINT pkey_role_id PRIMARY KEY (id);


--
-- TOC entry 2910 (class 2606 OID 34304589)
-- Dependencies: 295 295
-- Name: pkey_role_operation_xref_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY role_operation_xref
    ADD CONSTRAINT pkey_role_operation_xref_id PRIMARY KEY (id);


--
-- TOC entry 2914 (class 2606 OID 34304591)
-- Dependencies: 296 296
-- Name: pkey_status_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY status
    ADD CONSTRAINT pkey_status_id PRIMARY KEY (id);


--
-- TOC entry 2920 (class 2606 OID 34304593)
-- Dependencies: 298 298
-- Name: pkey_user_data_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_data
    ADD CONSTRAINT pkey_user_data_id PRIMARY KEY (id);


--
-- TOC entry 2887 (class 2606 OID 34304595)
-- Dependencies: 289 289
-- Name: pkey_user_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT pkey_user_id PRIMARY KEY (id);


--
-- TOC entry 2864 (class 2606 OID 34304597)
-- Dependencies: 284 284
-- Name: unq_group_data_name; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY group_data
    ADD CONSTRAINT unq_group_data_name UNIQUE (name);


--
-- TOC entry 2868 (class 2606 OID 34304599)
-- Dependencies: 285 285
-- Name: unq_group_data_type_name; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY group_data_type
    ADD CONSTRAINT unq_group_data_type_name UNIQUE (name);


--
-- TOC entry 2859 (class 2606 OID 34304601)
-- Dependencies: 283 283
-- Name: unq_group_name; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY "group"
    ADD CONSTRAINT unq_group_name UNIQUE (name);


--
-- TOC entry 2884 (class 2606 OID 34304603)
-- Dependencies: 288 288 288 288
-- Name: unq_membership_user_id_group_id_role_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY membership
    ADD CONSTRAINT unq_membership_user_id_group_id_role_id UNIQUE (user_id, group_id, role_id);


--
-- TOC entry 2893 (class 2606 OID 34304605)
-- Dependencies: 291 291
-- Name: unq_operation_name; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY operation
    ADD CONSTRAINT unq_operation_name UNIQUE (name);


--
-- TOC entry 2900 (class 2606 OID 34304607)
-- Dependencies: 292 292 292 292
-- Name: unq_permission_resource_id_principal_id_operation_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT unq_permission_resource_id_principal_id_operation_id UNIQUE (resource_id, principal_id, operation_id);


--
-- TOC entry 2906 (class 2606 OID 34304609)
-- Dependencies: 294 294
-- Name: unq_role_name; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY role
    ADD CONSTRAINT unq_role_name UNIQUE (name);


--
-- TOC entry 2912 (class 2606 OID 34304611)
-- Dependencies: 295 295 295
-- Name: unq_role_operation_xref_role_id_operation_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY role_operation_xref
    ADD CONSTRAINT unq_role_operation_xref_role_id_operation_id UNIQUE (role_id, operation_id);


--
-- TOC entry 2916 (class 2606 OID 34304613)
-- Dependencies: 296 296
-- Name: unq_status_name; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY status
    ADD CONSTRAINT unq_status_name UNIQUE (name);


--
-- TOC entry 2922 (class 2606 OID 34304615)
-- Dependencies: 298 298 298
-- Name: unq_user_data_user_id_group_data_id; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY user_data
    ADD CONSTRAINT unq_user_data_user_id_group_data_id UNIQUE (user_id, group_data_id);


--
-- TOC entry 2889 (class 2606 OID 34304617)
-- Dependencies: 289 289
-- Name: unq_user_openid; Type: CONSTRAINT; Schema: security; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT unq_user_openid UNIQUE (openid);


SET search_path = workspace, pg_catalog;

--
-- TOC entry 2924 (class 2606 OID 34304619)
-- Dependencies: 299 299
-- Name: charge_code_pkey; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY charge_code
    ADD CONSTRAINT charge_code_pkey PRIMARY KEY (id);


--
-- TOC entry 2926 (class 2606 OID 34304621)
-- Dependencies: 300 300
-- Name: data_transfer_item_pkey; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_transfer_item
    ADD CONSTRAINT data_transfer_item_pkey PRIMARY KEY (id);


--
-- TOC entry 2938 (class 2606 OID 34304623)
-- Dependencies: 302 302
-- Name: data_transfer_request_error_pkey; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_transfer_request_error
    ADD CONSTRAINT data_transfer_request_error_pkey PRIMARY KEY (id);


--
-- TOC entry 2931 (class 2606 OID 34304625)
-- Dependencies: 301 301
-- Name: data_transfer_request_pkey; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_transfer_request
    ADD CONSTRAINT data_transfer_request_pkey PRIMARY KEY (id);


--
-- TOC entry 2941 (class 2606 OID 34304627)
-- Dependencies: 303 303
-- Name: data_transfer_request_status_pkey; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY data_transfer_request_status
    ADD CONSTRAINT data_transfer_request_status_pkey PRIMARY KEY (id);


--
-- TOC entry 2944 (class 2606 OID 34304629)
-- Dependencies: 304 304
-- Name: pkey_search_criteria_id; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY search_criteria
    ADD CONSTRAINT pkey_search_criteria_id PRIMARY KEY (id);


--
-- TOC entry 2947 (class 2606 OID 34304631)
-- Dependencies: 305 305
-- Name: pkey_search_facet_id; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY search_facet
    ADD CONSTRAINT pkey_search_facet_id PRIMARY KEY (id);


--
-- TOC entry 2952 (class 2606 OID 34304633)
-- Dependencies: 306 306
-- Name: pkey_search_result_id; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY search_result
    ADD CONSTRAINT pkey_search_result_id PRIMARY KEY (id);


--
-- TOC entry 2957 (class 2606 OID 34304635)
-- Dependencies: 307 307
-- Name: pkey_workspace_id; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY workspace
    ADD CONSTRAINT pkey_workspace_id PRIMARY KEY (id);


--
-- TOC entry 2949 (class 2606 OID 34304637)
-- Dependencies: 305 305 305
-- Name: unq_search_facet_name_search_criteria_id; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY search_facet
    ADD CONSTRAINT unq_search_facet_name_search_criteria_id UNIQUE (name, search_criteria_id);


--
-- TOC entry 2954 (class 2606 OID 34304639)
-- Dependencies: 306 306 306
-- Name: unq_search_result_rdf_id_workspace_id; Type: CONSTRAINT; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

ALTER TABLE ONLY search_result
    ADD CONSTRAINT unq_search_result_rdf_id_workspace_id UNIQUE (rdf_id, workspace_id);


SET search_path = metadata, pg_catalog;

--
-- TOC entry 2757 (class 1259 OID 34304640)
-- Dependencies: 250 250
-- Name: entity_type_pkey_index; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE UNIQUE INDEX entity_type_pkey_index ON resource_type USING btree (id, description);


--
-- TOC entry 2447 (class 1259 OID 34304641)
-- Dependencies: 165
-- Name: fki_fkey_activity_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_fkey_activity_type_id ON activity USING btree (activity_type_id);


--
-- TOC entry 2540 (class 1259 OID 34304642)
-- Dependencies: 195
-- Name: fki_fkey_dataset_access_point_logical_file; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_fkey_dataset_access_point_logical_file ON dataset_access_point USING btree (dataset_id);


--
-- TOC entry 2746 (class 1259 OID 34304643)
-- Dependencies: 248
-- Name: fki_fkey_entity_type; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_fkey_entity_type ON resource USING btree (type_id);


--
-- TOC entry 2634 (class 1259 OID 34304644)
-- Dependencies: 218
-- Name: fki_fkey_file_access_point_logical_file; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_fkey_file_access_point_logical_file ON file_access_point USING btree (logical_file_id);


--
-- TOC entry 2534 (class 1259 OID 34304645)
-- Dependencies: 194
-- Name: fki_fkey_parent_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_fkey_parent_dataset_id ON dataset USING btree (parent_dataset_id);


--
-- TOC entry 2711 (class 1259 OID 34304646)
-- Dependencies: 239
-- Name: fki_fkey_persistent_identifier_authority; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_fkey_persistent_identifier_authority ON persistent_identifier USING btree (type_id);


--
-- TOC entry 2712 (class 1259 OID 34304647)
-- Dependencies: 239
-- Name: fki_fkey_persistent_identifier_entity; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_fkey_persistent_identifier_entity ON persistent_identifier USING btree (resource_id);


--
-- TOC entry 2760 (class 1259 OID 34304648)
-- Dependencies: 252
-- Name: fki_fkey_standard_name_type; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_fkey_standard_name_type ON standard_name USING btree (type_id);


--
-- TOC entry 2794 (class 1259 OID 34304649)
-- Dependencies: 263
-- Name: fki_fkey_variable_standard_name_xref_standard_name; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_fkey_variable_standard_name_xref_standard_name ON variable_standard_name_xref USING btree (standard_name_id);


--
-- TOC entry 2480 (class 1259 OID 34304650)
-- Dependencies: 178
-- Name: fki_logical_file_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX fki_logical_file_id ON checksum USING btree (logical_file_id);


--
-- TOC entry 2448 (class 1259 OID 34304651)
-- Dependencies: 165
-- Name: idx_activity_activity_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_activity_activity_type_id ON activity USING btree (activity_type_id);


--
-- TOC entry 2453 (class 1259 OID 34304652)
-- Dependencies: 166
-- Name: idx_activity_link_activity_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_activity_link_activity_id ON activity_link USING btree (activity_id);


--
-- TOC entry 2454 (class 1259 OID 34304653)
-- Dependencies: 166
-- Name: idx_activity_link_link_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_activity_link_link_type_id ON activity_link USING btree (link_type_id);


--
-- TOC entry 2465 (class 1259 OID 34304654)
-- Dependencies: 172
-- Name: idx_c_d_m_c_r_t_xref_cadis_descriptive_metadata_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_c_d_m_c_r_t_xref_cadis_descriptive_metadata_id ON cadis_descriptive_metadata_cadis_resolution_type_xref USING btree (cadis_descriptive_metadata_id);


--
-- TOC entry 2466 (class 1259 OID 34304655)
-- Dependencies: 172
-- Name: idx_c_d_m_c_r_t_xref_cadis_resolution_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_c_d_m_c_r_t_xref_cadis_resolution_type_id ON cadis_descriptive_metadata_cadis_resolution_type_xref USING btree (cadis_resolution_type_id);


--
-- TOC entry 2477 (class 1259 OID 34304656)
-- Dependencies: 177
-- Name: idx_campaign_activity_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_campaign_activity_id ON campaign USING btree (id);


--
-- TOC entry 2484 (class 1259 OID 34304657)
-- Dependencies: 180
-- Name: idx_contact_user_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_contact_user_id ON contact USING btree (user_id);


--
-- TOC entry 2491 (class 1259 OID 34304658)
-- Dependencies: 182
-- Name: idx_coordinate_axis_coordinate_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_coordinate_axis_coordinate_type_id ON coordinate_axis USING btree (coordinate_type_id);


--
-- TOC entry 2492 (class 1259 OID 34304659)
-- Dependencies: 182
-- Name: idx_coordinate_axis_geophysical_properties_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_coordinate_axis_geophysical_properties_id ON coordinate_axis USING btree (geophysical_properties_id);


--
-- TOC entry 2493 (class 1259 OID 34304660)
-- Dependencies: 182
-- Name: idx_coordinate_axis_unit_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_coordinate_axis_unit_id ON coordinate_axis USING btree (unit_id);


--
-- TOC entry 2505 (class 1259 OID 34304661)
-- Dependencies: 187
-- Name: idx_d_a_c_a_xref_data_access_application_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_a_c_a_xref_data_access_application_id ON data_access_capability_application_xref USING btree (data_access_capability_ref);


--
-- TOC entry 2506 (class 1259 OID 34304662)
-- Dependencies: 187
-- Name: idx_d_a_c_a_xref_data_access_application_ref; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_a_c_a_xref_data_access_application_ref ON data_access_capability_application_xref USING btree (data_access_application_ref);


--
-- TOC entry 2592 (class 1259 OID 34304663)
-- Dependencies: 208
-- Name: idx_d_m_d_t_xref_data_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_d_t_xref_data_type_id ON descriptive_metadata_data_type_xref USING btree (data_type_id);


--
-- TOC entry 2593 (class 1259 OID 34304664)
-- Dependencies: 208
-- Name: idx_d_m_d_t_xref_descriptive_metadata_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_d_t_xref_descriptive_metadata_id ON descriptive_metadata_data_type_xref USING btree (descriptive_metadata_id);


--
-- TOC entry 2596 (class 1259 OID 34304665)
-- Dependencies: 209
-- Name: idx_d_m_i_t_xref_descriptive_metadata_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_i_t_xref_descriptive_metadata_id ON descriptive_metadata_instrument_type_xref USING btree (descriptive_metadata_id);


--
-- TOC entry 2597 (class 1259 OID 34304666)
-- Dependencies: 209
-- Name: idx_d_m_i_t_xref_instrument_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_i_t_xref_instrument_type_id ON descriptive_metadata_instrument_type_xref USING btree (instrument_type_id);


--
-- TOC entry 2604 (class 1259 OID 34304667)
-- Dependencies: 211
-- Name: idx_d_m_l_xref_descriptive_metadata_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_l_xref_descriptive_metadata_id ON descriptive_metadata_location_xref USING btree (descriptive_metadata_id);


--
-- TOC entry 2605 (class 1259 OID 34304668)
-- Dependencies: 211
-- Name: idx_d_m_l_xref_location_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_l_xref_location_id ON descriptive_metadata_location_xref USING btree (location_id);


--
-- TOC entry 2608 (class 1259 OID 34304669)
-- Dependencies: 212
-- Name: idx_d_m_p_t_xref_descriptive_metadata_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_p_t_xref_descriptive_metadata_id ON descriptive_metadata_platform_type_xref USING btree (descriptive_metadata_id);


--
-- TOC entry 2609 (class 1259 OID 34304670)
-- Dependencies: 212
-- Name: idx_d_m_p_t_xref_platform_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_p_t_xref_platform_type_id ON descriptive_metadata_platform_type_xref USING btree (platform_type_id);


--
-- TOC entry 2612 (class 1259 OID 34304671)
-- Dependencies: 213
-- Name: idx_d_m_t_f_xref_descriptive_metadata_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_t_f_xref_descriptive_metadata_id ON descriptive_metadata_time_frequency_xref USING btree (descriptive_metadata_id);


--
-- TOC entry 2613 (class 1259 OID 34304672)
-- Dependencies: 213
-- Name: idx_d_m_t_f_xref_time_frequency_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_d_m_t_f_xref_time_frequency_id ON descriptive_metadata_time_frequency_xref USING btree (time_frequency_id);


--
-- TOC entry 2500 (class 1259 OID 34304673)
-- Dependencies: 186 186
-- Name: idx_data_access_capability_name_type; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_access_capability_name_type ON data_access_capability USING btree (name, capability_type_id);


--
-- TOC entry 2507 (class 1259 OID 34304674)
-- Dependencies: 188
-- Name: idx_data_access_capability_type_name; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_access_capability_type_name ON data_access_capability_type USING btree (name);


--
-- TOC entry 2543 (class 1259 OID 34304675)
-- Dependencies: 196
-- Name: idx_dataset_activity_xref_activty_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_activity_xref_activty_id ON dataset_activity_xref USING btree (activity_id);


--
-- TOC entry 2544 (class 1259 OID 34304676)
-- Dependencies: 196
-- Name: idx_dataset_activity_xref_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_activity_xref_dataset_id ON dataset_activity_xref USING btree (dataset_id);


--
-- TOC entry 2547 (class 1259 OID 34304677)
-- Dependencies: 197
-- Name: idx_dataset_citation_creator_contact_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_citation_creator_contact_id ON dataset_citation USING btree (creator_contact_id);


--
-- TOC entry 2548 (class 1259 OID 34304678)
-- Dependencies: 197
-- Name: idx_dataset_citation_descriptive_metadata_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_citation_descriptive_metadata_id ON dataset_citation USING btree (descriptive_metadata_id);


--
-- TOC entry 2549 (class 1259 OID 34304679)
-- Dependencies: 197
-- Name: idx_dataset_citation_publisher_contact_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_citation_publisher_contact_id ON dataset_citation USING btree (publisher_contact_id);


--
-- TOC entry 2552 (class 1259 OID 34304680)
-- Dependencies: 198
-- Name: idx_dataset_contact_xref_contact_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_contact_xref_contact_id ON dataset_contact_xref USING btree (contact_id);


--
-- TOC entry 2553 (class 1259 OID 34304681)
-- Dependencies: 198
-- Name: idx_dataset_contact_xref_contact_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_contact_xref_contact_type_id ON dataset_contact_xref USING btree (contact_type_id);


--
-- TOC entry 2554 (class 1259 OID 34304682)
-- Dependencies: 198
-- Name: idx_dataset_contact_xref_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_contact_xref_dataset_id ON dataset_contact_xref USING btree (dataset_id);


--
-- TOC entry 2557 (class 1259 OID 34304683)
-- Dependencies: 199
-- Name: idx_dataset_data_format_xref_data_format_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_data_format_xref_data_format_id ON dataset_data_format_xref USING btree (data_format_id);


--
-- TOC entry 2558 (class 1259 OID 34304684)
-- Dependencies: 199
-- Name: idx_dataset_data_format_xref_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_data_format_xref_dataset_id ON dataset_data_format_xref USING btree (dataset_id);


--
-- TOC entry 2535 (class 1259 OID 34304685)
-- Dependencies: 194
-- Name: idx_dataset_dataset_license_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_dataset_license_id ON dataset USING btree (license_id);


--
-- TOC entry 2536 (class 1259 OID 34304686)
-- Dependencies: 194
-- Name: idx_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_id ON dataset USING btree (id);


--
-- TOC entry 2537 (class 1259 OID 34304687)
-- Dependencies: 194
-- Name: idx_dataset_model_component_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_model_component_id ON dataset USING btree (model_component_id);


--
-- TOC entry 2563 (class 1259 OID 34304688)
-- Dependencies: 201
-- Name: idx_dataset_restriction_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_restriction_dataset_id ON dataset_restriction USING btree (id);


--
-- TOC entry 2566 (class 1259 OID 34304689)
-- Dependencies: 202
-- Name: idx_dataset_topic_xref_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_topic_xref_dataset_id ON dataset_topic_xref USING btree (dataset_id);


--
-- TOC entry 2567 (class 1259 OID 34304690)
-- Dependencies: 202
-- Name: idx_dataset_topic_xref_topic_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_topic_xref_topic_id ON dataset_topic_xref USING btree (topic_id);


--
-- TOC entry 2570 (class 1259 OID 34304691)
-- Dependencies: 203
-- Name: idx_dataset_typed_contact_contact_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_typed_contact_contact_id ON dataset_typed_contact USING btree (contact_id);


--
-- TOC entry 2571 (class 1259 OID 34304692)
-- Dependencies: 203
-- Name: idx_dataset_typed_contact_contact_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_typed_contact_contact_type_id ON dataset_typed_contact USING btree (contact_type_id);


--
-- TOC entry 2572 (class 1259 OID 34304693)
-- Dependencies: 203
-- Name: idx_dataset_typed_contact_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_typed_contact_dataset_id ON dataset_typed_contact USING btree (dataset_id);


--
-- TOC entry 2575 (class 1259 OID 34304694)
-- Dependencies: 204 204
-- Name: idx_dataset_version_ids; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_version_ids ON dataset_version USING btree (id, dataset_id);


--
-- TOC entry 2576 (class 1259 OID 34304695)
-- Dependencies: 204
-- Name: idx_dataset_version_published_state_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_version_published_state_id ON dataset_version USING btree (published_state_id);


--
-- TOC entry 2577 (class 1259 OID 34304696)
-- Dependencies: 204
-- Name: idx_dataset_version_user_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_version_user_id ON dataset_version USING btree (publisher);


--
-- TOC entry 2582 (class 1259 OID 34304697)
-- Dependencies: 206
-- Name: idx_dataset_version_variable_xref_dataset_version_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_version_variable_xref_dataset_version_id ON dataset_version_variable_xref USING btree (dataset_version_id);


--
-- TOC entry 2583 (class 1259 OID 34304698)
-- Dependencies: 206
-- Name: idx_dataset_version_variable_xref_variable_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_dataset_version_variable_xref_variable_id ON dataset_version_variable_xref USING btree (variable_id);


--
-- TOC entry 2588 (class 1259 OID 34304699)
-- Dependencies: 207
-- Name: idx_descriptive_metadata_data_product_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_descriptive_metadata_data_product_type_id ON descriptive_metadata USING btree (data_product_type_id);


--
-- TOC entry 2589 (class 1259 OID 34304700)
-- Dependencies: 207
-- Name: idx_descriptive_metadata_dataset_progress_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_descriptive_metadata_dataset_progress_id ON descriptive_metadata USING btree (dataset_progress_id);


--
-- TOC entry 2600 (class 1259 OID 34304701)
-- Dependencies: 210
-- Name: idx_descriptive_metadata_link_descriptive_metadata_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_descriptive_metadata_link_descriptive_metadata_id ON descriptive_metadata_link USING btree (descriptive_metadata_id);


--
-- TOC entry 2601 (class 1259 OID 34304702)
-- Dependencies: 210
-- Name: idx_descriptive_metadata_link_link_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_descriptive_metadata_link_link_type_id ON descriptive_metadata_link USING btree (link_type_id);


--
-- TOC entry 2590 (class 1259 OID 34304703)
-- Dependencies: 207
-- Name: idx_descriptive_metadata_physical_domain_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_descriptive_metadata_physical_domain_id ON descriptive_metadata USING btree (physical_domain_id);


--
-- TOC entry 2591 (class 1259 OID 34304704)
-- Dependencies: 207
-- Name: idx_descriptive_metadata_resolution_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_descriptive_metadata_resolution_type_id ON descriptive_metadata USING btree (resolution_type_id);


--
-- TOC entry 2616 (class 1259 OID 34304705)
-- Dependencies: 214
-- Name: idx_ensemble_activity_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_ensemble_activity_id ON ensemble USING btree (id);


--
-- TOC entry 2621 (class 1259 OID 34304706)
-- Dependencies: 216
-- Name: idx_experiment_activity_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_experiment_activity_id ON experiment USING btree (id);


--
-- TOC entry 2635 (class 1259 OID 34304707)
-- Dependencies: 218
-- Name: idx_file_access_point_storage_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_file_access_point_storage_type_id ON file_access_point USING btree (storage_type_id);


--
-- TOC entry 2636 (class 1259 OID 34304708)
-- Dependencies: 219
-- Name: idx_forcing_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_forcing_type_id ON forcing USING btree (type);


--
-- TOC entry 2645 (class 1259 OID 34304709)
-- Dependencies: 221
-- Name: idx_gateway_metadata_profile_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_gateway_metadata_profile_id ON gateway USING btree (metadata_profile_id);


--
-- TOC entry 2658 (class 1259 OID 34304710)
-- Dependencies: 222
-- Name: idx_gateway_specific_metadata_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_gateway_specific_metadata_dataset_id ON gateway_specific_metadata USING btree (dataset_ref);


--
-- TOC entry 2661 (class 1259 OID 34304711)
-- Dependencies: 223
-- Name: idx_geophysical_properties_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_geophysical_properties_dataset_id ON geophysical_properties USING btree (id);


--
-- TOC entry 2662 (class 1259 OID 34304712)
-- Dependencies: 223
-- Name: idx_geophysical_properties_grid_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_geophysical_properties_grid_id ON geophysical_properties USING btree (grid_id);


--
-- TOC entry 2665 (class 1259 OID 34304713)
-- Dependencies: 224
-- Name: idx_geospatial_coordinate_axis_coordinate_axis_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_geospatial_coordinate_axis_coordinate_axis_id ON geospatial_coordinate_axis USING btree (id);


--
-- TOC entry 2693 (class 1259 OID 34304714)
-- Dependencies: 233
-- Name: idx_l_f_v_xref_logical_file_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_l_f_v_xref_logical_file_id ON logical_file_variable_xref USING btree (logical_file_id);


--
-- TOC entry 2694 (class 1259 OID 34304715)
-- Dependencies: 233
-- Name: idx_l_f_v_xref_variable_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_l_f_v_xref_variable_id ON logical_file_variable_xref USING btree (variable_id);


--
-- TOC entry 2682 (class 1259 OID 34304716)
-- Dependencies: 230
-- Name: idx_location_taxonomy_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_location_taxonomy_id ON location USING btree (taxonomy_id);


--
-- TOC entry 2685 (class 1259 OID 34304717)
-- Dependencies: 231
-- Name: idx_logical_file_data_format_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_logical_file_data_format_id ON logical_file USING btree (data_format_id);


--
-- TOC entry 2686 (class 1259 OID 34304718)
-- Dependencies: 231
-- Name: idx_logical_file_lineage_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_logical_file_lineage_id ON logical_file USING btree (lineage_id);


--
-- TOC entry 2703 (class 1259 OID 34304719)
-- Dependencies: 236
-- Name: idx_model_component_resource_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_model_component_resource_id ON model_component USING btree (id);


--
-- TOC entry 2710 (class 1259 OID 34304720)
-- Dependencies: 238
-- Name: idx_note_resource_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_note_resource_id ON note USING btree (resource_ref);


--
-- TOC entry 2713 (class 1259 OID 34304721)
-- Dependencies: 239
-- Name: idx_persistent_identifier_resource_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_persistent_identifier_resource_id ON persistent_identifier USING btree (resource_id);


--
-- TOC entry 2728 (class 1259 OID 34304722)
-- Dependencies: 243
-- Name: idx_project_activity_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_project_activity_id ON project USING btree (id);


--
-- TOC entry 2729 (class 1259 OID 34304723)
-- Dependencies: 243
-- Name: idx_project_dataset_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_project_dataset_id ON project USING btree (dataset_id);


--
-- TOC entry 2751 (class 1259 OID 34304724)
-- Dependencies: 249
-- Name: idx_resource_tag_xref_resourse_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_resource_tag_xref_resourse_id ON resource_tag_xref USING btree (resource_ref);


--
-- TOC entry 2752 (class 1259 OID 34304725)
-- Dependencies: 249
-- Name: idx_resource_tag_xref_tag_ref; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_resource_tag_xref_tag_ref ON resource_tag_xref USING btree (tag_ref);


--
-- TOC entry 2761 (class 1259 OID 34304726)
-- Dependencies: 252 252
-- Name: idx_standard_name_name; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_standard_name_name ON standard_name USING btree (lower((name)::text));


--
-- TOC entry 2776 (class 1259 OID 34304727)
-- Dependencies: 257
-- Name: idx_time_coordinate_axis_coordinate_axis_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_time_coordinate_axis_coordinate_axis_id ON time_coordinate_axis USING btree (id);


--
-- TOC entry 2783 (class 1259 OID 34304728)
-- Dependencies: 259
-- Name: idx_topic_taxonomy_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_topic_taxonomy_id ON topic USING btree (taxonomy_id);


--
-- TOC entry 2786 (class 1259 OID 34304729)
-- Dependencies: 260 260
-- Name: idx_unit_symbol; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_unit_symbol ON unit USING btree (lower((symbol)::text));


--
-- TOC entry 2791 (class 1259 OID 34304730)
-- Dependencies: 262
-- Name: idx_variable_dataset_version_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_variable_dataset_version_id ON variable USING btree (dataset_version_id);


--
-- TOC entry 2795 (class 1259 OID 34304731)
-- Dependencies: 263
-- Name: idx_variable_standard_name_xref_variable_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_variable_standard_name_xref_variable_id ON variable_standard_name_xref USING btree (variable_id);


--
-- TOC entry 2798 (class 1259 OID 34304732)
-- Dependencies: 264
-- Name: idx_z_coordinate_axis_geospatial_coordinate_axis_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_z_coordinate_axis_geospatial_coordinate_axis_id ON z_coordinate_axis USING btree (id);


--
-- TOC entry 2799 (class 1259 OID 34304733)
-- Dependencies: 264
-- Name: idx_z_coordinate_axis_z_positive_type_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_z_coordinate_axis_z_positive_type_id ON z_coordinate_axis USING btree (z_positive_type_id);


--
-- TOC entry 2747 (class 1259 OID 34304734)
-- Dependencies: 248 248
-- Name: index_entity_type; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX index_entity_type ON resource USING btree (id, type_id);


--
-- TOC entry 2689 (class 1259 OID 34304735)
-- Dependencies: 232
-- Name: logical_file_dataset_version_xref_dataset_version_id; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX logical_file_dataset_version_xref_dataset_version_id ON logical_file_dataset_version_xref USING btree (dataset_version_id);


--
-- TOC entry 2690 (class 1259 OID 34304736)
-- Dependencies: 232 232
-- Name: logical_file_dataset_version_xref_full; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX logical_file_dataset_version_xref_full ON logical_file_dataset_version_xref USING btree (logical_file_id, dataset_version_id);


--
-- TOC entry 2750 (class 1259 OID 34304737)
-- Dependencies: 248
-- Name: pki_entity; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE UNIQUE INDEX pki_entity ON resource USING btree (id);


--
-- TOC entry 2483 (class 1259 OID 34304738)
-- Dependencies: 178 178
-- Name: unq_checksum_unique_algorithm; Type: INDEX; Schema: metadata; Owner: esgcet_admin; Tablespace: 
--

CREATE UNIQUE INDEX unq_checksum_unique_algorithm ON checksum USING btree (logical_file_id, algorithm);


SET search_path = metrics, pg_catalog;

--
-- TOC entry 2806 (class 1259 OID 34304739)
-- Dependencies: 267
-- Name: idx_clickstream_user_agent_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_clickstream_user_agent_id ON clickstream USING btree (user_agent_id);


--
-- TOC entry 2807 (class 1259 OID 34304740)
-- Dependencies: 267
-- Name: idx_clickstream_users_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_clickstream_users_id ON clickstream USING btree (user_id);


--
-- TOC entry 2812 (class 1259 OID 34304741)
-- Dependencies: 269
-- Name: idx_file_download_file_access_point_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_file_download_file_access_point_id ON file_download USING btree (file_access_point_id);


--
-- TOC entry 2813 (class 1259 OID 34304742)
-- Dependencies: 269
-- Name: idx_file_download_user_agent_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_file_download_user_agent_id ON file_download USING btree (user_agent_id);


--
-- TOC entry 2814 (class 1259 OID 34304743)
-- Dependencies: 269
-- Name: idx_file_download_users_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_file_download_users_id ON file_download USING btree (user_id);


--
-- TOC entry 2823 (class 1259 OID 34304744)
-- Dependencies: 272 272 272
-- Name: idx_user_agent_ident; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE UNIQUE INDEX idx_user_agent_ident ON user_agent USING btree (id, name, ignore);

ALTER TABLE user_agent CLUSTER ON idx_user_agent_ident;


--
-- TOC entry 2824 (class 1259 OID 34304745)
-- Dependencies: 272
-- Name: idx_user_agent_operating_system_type_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_user_agent_operating_system_type_id ON user_agent USING btree (operating_system_type_id);


--
-- TOC entry 2825 (class 1259 OID 34304746)
-- Dependencies: 272
-- Name: idx_user_agent_user_agent_type; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_user_agent_user_agent_type ON user_agent USING btree (user_agent_type_id);


--
-- TOC entry 2834 (class 1259 OID 34304747)
-- Dependencies: 274
-- Name: idx_user_logic_user_login_type_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_user_logic_user_login_type_id ON user_login USING btree (user_login_type_id);


--
-- TOC entry 2835 (class 1259 OID 34304748)
-- Dependencies: 274
-- Name: idx_user_login_users_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_user_login_users_id ON user_login USING btree (users_id);


--
-- TOC entry 2843 (class 1259 OID 34304749)
-- Dependencies: 277
-- Name: idx_user_search_facet_user_search_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_user_search_facet_user_search_id ON user_search_facet USING btree (user_search_id);


--
-- TOC entry 2840 (class 1259 OID 34304750)
-- Dependencies: 276
-- Name: idx_user_search_users_id; Type: INDEX; Schema: metrics; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_user_search_users_id ON user_search USING btree (users_id);


SET search_path = security, pg_catalog;

--
-- TOC entry 2860 (class 1259 OID 34304751)
-- Dependencies: 284
-- Name: idx_group_data_group_data_type_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_group_data_group_data_type_id ON group_data USING btree (group_data_type_id);


--
-- TOC entry 2869 (class 1259 OID 34304752)
-- Dependencies: 286
-- Name: idx_group_default_role_group_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_group_default_role_group_id ON group_default_role USING btree (group_id);


--
-- TOC entry 2870 (class 1259 OID 34304753)
-- Dependencies: 286
-- Name: idx_group_default_role_role_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_group_default_role_role_id ON group_default_role USING btree (role_id);


--
-- TOC entry 2873 (class 1259 OID 34304754)
-- Dependencies: 287
-- Name: idx_group_group_data_xref_group_data_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_group_group_data_xref_group_data_id ON group_group_data_xref USING btree (group_data_id);


--
-- TOC entry 2874 (class 1259 OID 34304755)
-- Dependencies: 287
-- Name: idx_group_group_data_xref_group_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_group_group_data_xref_group_id ON group_group_data_xref USING btree (group_id);


--
-- TOC entry 2855 (class 1259 OID 34304756)
-- Dependencies: 283
-- Name: idx_group_principal_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_group_principal_id ON "group" USING btree (id);


--
-- TOC entry 2877 (class 1259 OID 34304757)
-- Dependencies: 288
-- Name: idx_membership_group_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_membership_group_id ON membership USING btree (group_id);


--
-- TOC entry 2878 (class 1259 OID 34304758)
-- Dependencies: 288
-- Name: idx_membership_role_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_membership_role_id ON membership USING btree (role_id);


--
-- TOC entry 2879 (class 1259 OID 34304759)
-- Dependencies: 288
-- Name: idx_membership_status_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_membership_status_id ON membership USING btree (status_id);


--
-- TOC entry 2880 (class 1259 OID 34304760)
-- Dependencies: 288
-- Name: idx_membership_user_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_membership_user_id ON membership USING btree (user_id);


--
-- TOC entry 2894 (class 1259 OID 34304761)
-- Dependencies: 292
-- Name: idx_permission_operation_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_permission_operation_id ON permission USING btree (operation_id);


--
-- TOC entry 2895 (class 1259 OID 34304762)
-- Dependencies: 292
-- Name: idx_permission_principal_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_permission_principal_id ON permission USING btree (principal_id);


--
-- TOC entry 2896 (class 1259 OID 34304763)
-- Dependencies: 292
-- Name: idx_permission_resource_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_permission_resource_id ON permission USING btree (resource_id);


--
-- TOC entry 2907 (class 1259 OID 34304764)
-- Dependencies: 295
-- Name: idx_role_operation_xref_operation_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_role_operation_xref_operation_id ON role_operation_xref USING btree (operation_id);


--
-- TOC entry 2908 (class 1259 OID 34304765)
-- Dependencies: 295
-- Name: idx_role_operation_xref_role_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_role_operation_xref_role_id ON role_operation_xref USING btree (role_id);


--
-- TOC entry 2852 (class 1259 OID 34304766)
-- Dependencies: 282
-- Name: idx_security_authorization_token_user_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_security_authorization_token_user_id ON authorization_token USING btree (user_id);


--
-- TOC entry 2917 (class 1259 OID 34304767)
-- Dependencies: 298
-- Name: idx_user_data_group_data_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_user_data_group_data_id ON user_data USING btree (group_data_id);


--
-- TOC entry 2918 (class 1259 OID 34304768)
-- Dependencies: 298
-- Name: idx_user_data_user_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_user_data_user_id ON user_data USING btree (user_id);


--
-- TOC entry 2885 (class 1259 OID 34304769)
-- Dependencies: 289
-- Name: idx_user_principal_id; Type: INDEX; Schema: security; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_user_principal_id ON "user" USING btree (id);


SET search_path = workspace, pg_catalog;

--
-- TOC entry 2927 (class 1259 OID 34304770)
-- Dependencies: 300
-- Name: idx_data_transfer_item_error_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_transfer_item_error_id ON data_transfer_item USING btree (error_id);


--
-- TOC entry 2928 (class 1259 OID 34304771)
-- Dependencies: 300
-- Name: idx_data_transfer_item_request_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_transfer_item_request_id ON data_transfer_item USING btree (request_id);


--
-- TOC entry 2929 (class 1259 OID 34304772)
-- Dependencies: 300
-- Name: idx_data_transfer_item_status_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_transfer_item_status_id ON data_transfer_item USING btree (status_id);


--
-- TOC entry 2932 (class 1259 OID 34304773)
-- Dependencies: 301
-- Name: idx_data_transfer_request_data_access_capability_type_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_transfer_request_data_access_capability_type_id ON data_transfer_request USING btree (data_access_capability_type_id);


--
-- TOC entry 2933 (class 1259 OID 34304774)
-- Dependencies: 301
-- Name: idx_data_transfer_request_dataset_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_transfer_request_dataset_id ON data_transfer_request USING btree (dataset_id);


--
-- TOC entry 2939 (class 1259 OID 34304775)
-- Dependencies: 302
-- Name: idx_data_transfer_request_error_data_access_capability_type_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_transfer_request_error_data_access_capability_type_id ON data_transfer_request_error USING btree (data_access_capability_type_id);


--
-- TOC entry 2934 (class 1259 OID 34304776)
-- Dependencies: 301
-- Name: idx_data_transfer_request_error_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_transfer_request_error_id ON data_transfer_request USING btree (error_id);


--
-- TOC entry 2935 (class 1259 OID 34304777)
-- Dependencies: 301
-- Name: idx_data_transfer_request_status_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_transfer_request_status_id ON data_transfer_request USING btree (status_id);


--
-- TOC entry 2936 (class 1259 OID 34304778)
-- Dependencies: 301
-- Name: idx_data_transfer_request_user_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_data_transfer_request_user_id ON data_transfer_request USING btree (user_id);


--
-- TOC entry 2942 (class 1259 OID 34304779)
-- Dependencies: 304
-- Name: idx_search_criteria_workspace_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_search_criteria_workspace_id ON search_criteria USING btree (workspace_id);


--
-- TOC entry 2945 (class 1259 OID 34304780)
-- Dependencies: 305
-- Name: idx_search_facet_search_criteria_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_search_facet_search_criteria_id ON search_facet USING btree (search_criteria_id);


--
-- TOC entry 2950 (class 1259 OID 34304781)
-- Dependencies: 306
-- Name: idx_search_result_workspace_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_search_result_workspace_id ON search_result USING btree (workspace_id);


--
-- TOC entry 2955 (class 1259 OID 34304782)
-- Dependencies: 307
-- Name: idx_workspace_principal_id; Type: INDEX; Schema: workspace; Owner: esgcet_admin; Tablespace: 
--

CREATE INDEX idx_workspace_principal_id ON workspace USING btree (principal_id);


SET search_path = metadata, pg_catalog;

--
-- TOC entry 3109 (class 2620 OID 34304783)
-- Dependencies: 320 165
-- Name: activity_update_timestamps; Type: TRIGGER; Schema: metadata; Owner: esgcet_admin
--

CREATE TRIGGER activity_update_timestamps BEFORE INSERT OR UPDATE ON activity FOR EACH ROW EXECUTE PROCEDURE persistent_object_update_timestamps();


--
-- TOC entry 3110 (class 2620 OID 34304784)
-- Dependencies: 186 320
-- Name: data_access_capability_date_updated; Type: TRIGGER; Schema: metadata; Owner: esgcet_admin
--

CREATE TRIGGER data_access_capability_date_updated AFTER UPDATE ON data_access_capability FOR EACH ROW EXECUTE PROCEDURE persistent_object_update_timestamps();


--
-- TOC entry 3112 (class 2620 OID 34304785)
-- Dependencies: 204 327
-- Name: dataset_version_after_update; Type: TRIGGER; Schema: metadata; Owner: esgcet_admin
--

CREATE TRIGGER dataset_version_after_update AFTER UPDATE ON dataset_version FOR EACH ROW EXECUTE PROCEDURE dataset_version_update_resource_timestamp();


--
-- TOC entry 3113 (class 2620 OID 34304786)
-- Dependencies: 248 320
-- Name: resource_update_timestamps; Type: TRIGGER; Schema: metadata; Owner: esgcet_admin
--

CREATE TRIGGER resource_update_timestamps BEFORE UPDATE ON resource FOR EACH ROW EXECUTE PROCEDURE persistent_object_update_timestamps();


--
-- TOC entry 3114 (class 2620 OID 34304787)
-- Dependencies: 252 320
-- Name: standard_name_update_timestamps; Type: TRIGGER; Schema: metadata; Owner: esgcet_admin
--

CREATE TRIGGER standard_name_update_timestamps BEFORE UPDATE ON standard_name FOR EACH ROW EXECUTE PROCEDURE persistent_object_update_timestamps();


--
-- TOC entry 3111 (class 2620 OID 34304789)
-- Dependencies: 321 194
-- Name: trigger_check_parent_display_order; Type: TRIGGER; Schema: metadata; Owner: esgcet_admin
--

CREATE CONSTRAINT TRIGGER trigger_check_parent_display_order AFTER INSERT OR UPDATE ON dataset DEFERRABLE INITIALLY DEFERRED FOR EACH ROW EXECUTE PROCEDURE func_check_parent_display_order();


--
-- TOC entry 2959 (class 2606 OID 34304790)
-- Dependencies: 165 2449 166
-- Name: fkey_activity_link_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY activity_link
    ADD CONSTRAINT fkey_activity_link_id FOREIGN KEY (activity_id) REFERENCES activity(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2960 (class 2606 OID 34304795)
-- Dependencies: 229 2680 166
-- Name: fkey_activity_link_link_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY activity_link
    ADD CONSTRAINT fkey_activity_link_link_type_id FOREIGN KEY (link_type_id) REFERENCES link_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2958 (class 2606 OID 34304800)
-- Dependencies: 165 167 2457
-- Name: fkey_activity_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY activity
    ADD CONSTRAINT fkey_activity_type_id FOREIGN KEY (activity_type_id) REFERENCES activity_type(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 2962 (class 2606 OID 34304805)
-- Dependencies: 172 2586 207
-- Name: fkey_cadis_descriptive_metadata_cadis_resolution_type_xref_cadi; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY cadis_descriptive_metadata_cadis_resolution_type_xref
    ADD CONSTRAINT fkey_cadis_descriptive_metadata_cadis_resolution_type_xref_cadi FOREIGN KEY (cadis_descriptive_metadata_id) REFERENCES descriptive_metadata(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2963 (class 2606 OID 34304810)
-- Dependencies: 176 172 2475
-- Name: fkey_cadis_descriptive_metadata_cadis_resolution_xref_cadis_res; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY cadis_descriptive_metadata_cadis_resolution_type_xref
    ADD CONSTRAINT fkey_cadis_descriptive_metadata_cadis_resolution_xref_cadis_res FOREIGN KEY (cadis_resolution_type_id) REFERENCES cadis_resolution_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2964 (class 2606 OID 34304815)
-- Dependencies: 173 243 2732
-- Name: fkey_cadis_project_project_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY cadis_project
    ADD CONSTRAINT fkey_cadis_project_project_id FOREIGN KEY (id) REFERENCES project(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2966 (class 2606 OID 34304820)
-- Dependencies: 174 2732 243
-- Name: fkey_cadis_project_project_id_xref; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY cadis_project_project_xref
    ADD CONSTRAINT fkey_cadis_project_project_id_xref FOREIGN KEY (project_id) REFERENCES project(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2967 (class 2606 OID 34304825)
-- Dependencies: 174 2469 173
-- Name: fkey_cadis_project_project_xref; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY cadis_project_project_xref
    ADD CONSTRAINT fkey_cadis_project_project_xref FOREIGN KEY (cadis_project_id) REFERENCES cadis_project(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2968 (class 2606 OID 34304830)
-- Dependencies: 2732 243 175
-- Name: fkey_cadis_project_typed_contact_cadis_project_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY cadis_project_typed_contact
    ADD CONSTRAINT fkey_cadis_project_typed_contact_cadis_project_id FOREIGN KEY (cadis_project_id) REFERENCES project(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2969 (class 2606 OID 34304835)
-- Dependencies: 175 2485 180
-- Name: fkey_cadis_project_typed_contact_contact_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY cadis_project_typed_contact
    ADD CONSTRAINT fkey_cadis_project_typed_contact_contact_id FOREIGN KEY (contact_id) REFERENCES contact(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2970 (class 2606 OID 34304840)
-- Dependencies: 175 2489 181
-- Name: fkey_cadis_project_typed_contact_contact_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY cadis_project_typed_contact
    ADD CONSTRAINT fkey_cadis_project_typed_contact_contact_type_id FOREIGN KEY (contact_type_id) REFERENCES contact_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2971 (class 2606 OID 34304845)
-- Dependencies: 165 2449 177
-- Name: fkey_campaign_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY campaign
    ADD CONSTRAINT fkey_campaign_id FOREIGN KEY (id) REFERENCES activity(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE;


--
-- TOC entry 2973 (class 2606 OID 34304850)
-- Dependencies: 289 2886 180
-- Name: fkey_contact_user_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY contact
    ADD CONSTRAINT fkey_contact_user_id FOREIGN KEY (user_id) REFERENCES security."user"(id) MATCH FULL ON UPDATE SET NULL ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3048 (class 2606 OID 34304855)
-- Dependencies: 243 2732 243
-- Name: fkey_continuation_of_project_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fkey_continuation_of_project_id FOREIGN KEY (continuation_of_project_id) REFERENCES project(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3049 (class 2606 OID 34304860)
-- Dependencies: 243 243 2732
-- Name: fkey_continuing_project_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fkey_continuing_project_id FOREIGN KEY (continuing_project_id) REFERENCES project(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2965 (class 2606 OID 34304865)
-- Dependencies: 2732 243 173
-- Name: fkey_continuing_project_id_project_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY cadis_project
    ADD CONSTRAINT fkey_continuing_project_id_project_id FOREIGN KEY (continuing_project_id) REFERENCES project(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2974 (class 2606 OID 34304870)
-- Dependencies: 183 2496 182
-- Name: fkey_coordinate_axis_coordinate_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY coordinate_axis
    ADD CONSTRAINT fkey_coordinate_axis_coordinate_type_id FOREIGN KEY (coordinate_type_id) REFERENCES coordinate_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2975 (class 2606 OID 34304875)
-- Dependencies: 223 182 2663
-- Name: fkey_coordinate_axis_geophysical_properties_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY coordinate_axis
    ADD CONSTRAINT fkey_coordinate_axis_geophysical_properties_id FOREIGN KEY (geophysical_properties_id) REFERENCES geophysical_properties(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2976 (class 2606 OID 34304880)
-- Dependencies: 182 260 2787
-- Name: fkey_coordinate_axis_unit_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY coordinate_axis
    ADD CONSTRAINT fkey_coordinate_axis_unit_id FOREIGN KEY (unit_id) REFERENCES unit(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2978 (class 2606 OID 34304885)
-- Dependencies: 184 2498 187
-- Name: fkey_data_access_application_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY data_access_capability_application_xref
    ADD CONSTRAINT fkey_data_access_application_id FOREIGN KEY (data_access_application_ref) REFERENCES data_access_application(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2979 (class 2606 OID 34304890)
-- Dependencies: 2501 187 186
-- Name: fkey_data_access_capability_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY data_access_capability_application_xref
    ADD CONSTRAINT fkey_data_access_capability_id FOREIGN KEY (data_access_capability_ref) REFERENCES data_access_capability(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2977 (class 2606 OID 34304895)
-- Dependencies: 186 188 2508
-- Name: fkey_data_access_capability_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY data_access_capability
    ADD CONSTRAINT fkey_data_access_capability_type_id FOREIGN KEY (capability_type_id) REFERENCES data_access_capability_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2980 (class 2606 OID 34304900)
-- Dependencies: 188 189 2512
-- Name: fkey_data_access_capability_type_protocol; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY data_access_capability_type
    ADD CONSTRAINT fkey_data_access_capability_type_protocol FOREIGN KEY (protocol_id) REFERENCES data_access_protocol(id) DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2986 (class 2606 OID 34304905)
-- Dependencies: 195 194 2538
-- Name: fkey_dataset_access_point_dataset; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_access_point
    ADD CONSTRAINT fkey_dataset_access_point_dataset FOREIGN KEY (dataset_id) REFERENCES dataset(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2987 (class 2606 OID 34304910)
-- Dependencies: 165 2449 196
-- Name: fkey_dataset_activity_xref_activity_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_activity_xref
    ADD CONSTRAINT fkey_dataset_activity_xref_activity_id FOREIGN KEY (activity_id) REFERENCES activity(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2988 (class 2606 OID 34304915)
-- Dependencies: 196 194 2538
-- Name: fkey_dataset_activity_xref_dataset_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_activity_xref
    ADD CONSTRAINT fkey_dataset_activity_xref_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2989 (class 2606 OID 34304920)
-- Dependencies: 180 2485 197
-- Name: fkey_dataset_citation_creator_contact_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_citation
    ADD CONSTRAINT fkey_dataset_citation_creator_contact_id FOREIGN KEY (creator_contact_id) REFERENCES contact(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2990 (class 2606 OID 34304925)
-- Dependencies: 207 197 2586
-- Name: fkey_dataset_citation_descriptive_metadata_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_citation
    ADD CONSTRAINT fkey_dataset_citation_descriptive_metadata_id FOREIGN KEY (descriptive_metadata_id) REFERENCES descriptive_metadata(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2991 (class 2606 OID 34304930)
-- Dependencies: 180 2485 197
-- Name: fkey_dataset_citation_publisher_contact_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_citation
    ADD CONSTRAINT fkey_dataset_citation_publisher_contact_id FOREIGN KEY (publisher_contact_id) REFERENCES contact(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2992 (class 2606 OID 34304935)
-- Dependencies: 180 2485 198
-- Name: fkey_dataset_contact_xref_contact_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_contact_xref
    ADD CONSTRAINT fkey_dataset_contact_xref_contact_id FOREIGN KEY (contact_id) REFERENCES contact(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2993 (class 2606 OID 34304940)
-- Dependencies: 198 2489 181
-- Name: fkey_dataset_contact_xref_contact_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_contact_xref
    ADD CONSTRAINT fkey_dataset_contact_xref_contact_type_id FOREIGN KEY (contact_type_id) REFERENCES contact_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2994 (class 2606 OID 34304945)
-- Dependencies: 194 198 2538
-- Name: fkey_dataset_contact_xref_dataset_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_contact_xref
    ADD CONSTRAINT fkey_dataset_contact_xref_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2982 (class 2606 OID 34304950)
-- Dependencies: 2748 248 194
-- Name: fkey_dataset_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset
    ADD CONSTRAINT fkey_dataset_id FOREIGN KEY (id) REFERENCES resource(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2983 (class 2606 OID 34304955)
-- Dependencies: 194 228 2678
-- Name: fkey_dataset_license; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset
    ADD CONSTRAINT fkey_dataset_license FOREIGN KEY (license_id) REFERENCES license(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2984 (class 2606 OID 34304960)
-- Dependencies: 236 2704 194
-- Name: fkey_dataset_model_component_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset
    ADD CONSTRAINT fkey_dataset_model_component_id FOREIGN KEY (model_component_id) REFERENCES model_component(id) DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3003 (class 2606 OID 34304965)
-- Dependencies: 244 204 2734
-- Name: fkey_dataset_published_state_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_version
    ADD CONSTRAINT fkey_dataset_published_state_id FOREIGN KEY (published_state_id) REFERENCES published_state(id);


--
-- TOC entry 2997 (class 2606 OID 34304970)
-- Dependencies: 201 194 2538
-- Name: fkey_dataset_restriction_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_restriction
    ADD CONSTRAINT fkey_dataset_restriction_id FOREIGN KEY (id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3000 (class 2606 OID 34304975)
-- Dependencies: 2485 203 180
-- Name: fkey_dataset_typed_contact_contact_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_typed_contact
    ADD CONSTRAINT fkey_dataset_typed_contact_contact_id FOREIGN KEY (contact_id) REFERENCES contact(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3001 (class 2606 OID 34304980)
-- Dependencies: 181 2489 203
-- Name: fkey_dataset_typed_contact_contact_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_typed_contact
    ADD CONSTRAINT fkey_dataset_typed_contact_contact_type_id FOREIGN KEY (contact_type_id) REFERENCES contact_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3002 (class 2606 OID 34304985)
-- Dependencies: 2538 203 194
-- Name: fkey_dataset_typed_contact_dataset_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_typed_contact
    ADD CONSTRAINT fkey_dataset_typed_contact_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3004 (class 2606 OID 34304990)
-- Dependencies: 2538 204 194
-- Name: fkey_dataset_version_dataset_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_version
    ADD CONSTRAINT fkey_dataset_version_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3006 (class 2606 OID 34304995)
-- Dependencies: 2578 206 204
-- Name: fkey_dataset_version_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_version_variable_xref
    ADD CONSTRAINT fkey_dataset_version_id FOREIGN KEY (dataset_version_id) REFERENCES dataset_version(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3005 (class 2606 OID 34305000)
-- Dependencies: 2886 204 289
-- Name: fkey_dataset_version_user_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_version
    ADD CONSTRAINT fkey_dataset_version_user_id FOREIGN KEY (publisher) REFERENCES security."user"(id) ON UPDATE RESTRICT DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2995 (class 2606 OID 34305005)
-- Dependencies: 2516 190 199
-- Name: fkey_dataset_xref_data_format_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_data_format_xref
    ADD CONSTRAINT fkey_dataset_xref_data_format_id FOREIGN KEY (data_format_id) REFERENCES data_format(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2998 (class 2606 OID 34305010)
-- Dependencies: 2538 202 194
-- Name: fkey_dataset_xref_dataset_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_topic_xref
    ADD CONSTRAINT fkey_dataset_xref_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2996 (class 2606 OID 34305015)
-- Dependencies: 199 194 2538
-- Name: fkey_dataset_xref_dataset_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_data_format_xref
    ADD CONSTRAINT fkey_dataset_xref_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2999 (class 2606 OID 34305020)
-- Dependencies: 259 2784 202
-- Name: fkey_dataset_xref_topic_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_topic_xref
    ADD CONSTRAINT fkey_dataset_xref_topic_id FOREIGN KEY (topic_id) REFERENCES topic(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3008 (class 2606 OID 34305025)
-- Dependencies: 207 2520 191
-- Name: fkey_descriptive_metadata_data_product_type_data_product_type_i; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata
    ADD CONSTRAINT fkey_descriptive_metadata_data_product_type_data_product_type_i FOREIGN KEY (data_product_type_id) REFERENCES data_product_type(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3012 (class 2606 OID 34305030)
-- Dependencies: 2524 192 208
-- Name: fkey_descriptive_metadata_data_type_xref_data_type; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_data_type_xref
    ADD CONSTRAINT fkey_descriptive_metadata_data_type_xref_data_type FOREIGN KEY (data_type_id) REFERENCES data_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3013 (class 2606 OID 34305035)
-- Dependencies: 2586 208 207
-- Name: fkey_descriptive_metadata_data_type_xref_descriptive_metadata; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_data_type_xref
    ADD CONSTRAINT fkey_descriptive_metadata_data_type_xref_descriptive_metadata FOREIGN KEY (descriptive_metadata_id) REFERENCES descriptive_metadata(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3009 (class 2606 OID 34305040)
-- Dependencies: 2561 207 200
-- Name: fkey_descriptive_metadata_dataset_progress_dataset_progress_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata
    ADD CONSTRAINT fkey_descriptive_metadata_dataset_progress_dataset_progress_id FOREIGN KEY (dataset_progress_id) REFERENCES dataset_progress(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3014 (class 2606 OID 34305045)
-- Dependencies: 2586 209 207
-- Name: fkey_descriptive_metadata_instrument_type_xref_descriptive_meta; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_instrument_type_xref
    ADD CONSTRAINT fkey_descriptive_metadata_instrument_type_xref_descriptive_meta FOREIGN KEY (descriptive_metadata_id) REFERENCES descriptive_metadata(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3015 (class 2606 OID 34305050)
-- Dependencies: 209 227 2674
-- Name: fkey_descriptive_metadata_instrument_type_xref_instrument_type_; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_instrument_type_xref
    ADD CONSTRAINT fkey_descriptive_metadata_instrument_type_xref_instrument_type_ FOREIGN KEY (instrument_type_id) REFERENCES instrument_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3016 (class 2606 OID 34305055)
-- Dependencies: 2586 207 210
-- Name: fkey_descriptive_metadata_link_descriptive_metadata_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_link
    ADD CONSTRAINT fkey_descriptive_metadata_link_descriptive_metadata_id FOREIGN KEY (descriptive_metadata_id) REFERENCES descriptive_metadata(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3017 (class 2606 OID 34305060)
-- Dependencies: 210 229 2680
-- Name: fkey_descriptive_metadata_link_link_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_link
    ADD CONSTRAINT fkey_descriptive_metadata_link_link_type_id FOREIGN KEY (link_type_id) REFERENCES link_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3018 (class 2606 OID 34305065)
-- Dependencies: 2586 211 207
-- Name: fkey_descriptive_metadata_location_xref_descriptive_metadata_de; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_location_xref
    ADD CONSTRAINT fkey_descriptive_metadata_location_xref_descriptive_metadata_de FOREIGN KEY (descriptive_metadata_id) REFERENCES descriptive_metadata(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3019 (class 2606 OID 34305070)
-- Dependencies: 211 2683 230
-- Name: fkey_descriptive_metadata_location_xref_location_location_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_location_xref
    ADD CONSTRAINT fkey_descriptive_metadata_location_xref_location_location_id FOREIGN KEY (location_id) REFERENCES location(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3010 (class 2606 OID 34305075)
-- Dependencies: 2722 207 241
-- Name: fkey_descriptive_metadata_physical_domain_physical_domain_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata
    ADD CONSTRAINT fkey_descriptive_metadata_physical_domain_physical_domain_id FOREIGN KEY (physical_domain_id) REFERENCES physical_domain(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3020 (class 2606 OID 34305080)
-- Dependencies: 2586 212 207
-- Name: fkey_descriptive_metadata_platform_type_xref_descriptive_metada; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_platform_type_xref
    ADD CONSTRAINT fkey_descriptive_metadata_platform_type_xref_descriptive_metada FOREIGN KEY (descriptive_metadata_id) REFERENCES descriptive_metadata(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3021 (class 2606 OID 34305085)
-- Dependencies: 212 242 2726
-- Name: fkey_descriptive_metadata_platform_type_xref_platform_type_plat; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_platform_type_xref
    ADD CONSTRAINT fkey_descriptive_metadata_platform_type_xref_platform_type_plat FOREIGN KEY (platform_type_id) REFERENCES platform_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3011 (class 2606 OID 34305090)
-- Dependencies: 247 207 2742
-- Name: fkey_descriptive_metadata_resolution_type_resolution_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata
    ADD CONSTRAINT fkey_descriptive_metadata_resolution_type_resolution_type_id FOREIGN KEY (resolution_type_id) REFERENCES resolution_type(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3022 (class 2606 OID 34305095)
-- Dependencies: 213 207 2586
-- Name: fkey_descriptive_metadata_time_frequency_xref_descriptive_metad; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_time_frequency_xref
    ADD CONSTRAINT fkey_descriptive_metadata_time_frequency_xref_descriptive_metad FOREIGN KEY (descriptive_metadata_id) REFERENCES descriptive_metadata(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3023 (class 2606 OID 34305100)
-- Dependencies: 213 2779 258
-- Name: fkey_descriptive_metadata_time_frequency_xref_time_frequency; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY descriptive_metadata_time_frequency_xref
    ADD CONSTRAINT fkey_descriptive_metadata_time_frequency_xref_time_frequency FOREIGN KEY (time_frequency_id) REFERENCES time_frequency(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3024 (class 2606 OID 34305105)
-- Dependencies: 165 214 2449
-- Name: fkey_ensemble_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY ensemble
    ADD CONSTRAINT fkey_ensemble_id FOREIGN KEY (id) REFERENCES activity(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE;


--
-- TOC entry 3025 (class 2606 OID 34305110)
-- Dependencies: 165 216 2449
-- Name: fkey_experiment_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY experiment
    ADD CONSTRAINT fkey_experiment_id FOREIGN KEY (id) REFERENCES activity(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE;


--
-- TOC entry 2981 (class 2606 OID 34305115)
-- Dependencies: 217 2628 193
-- Name: fkey_federation_gateway_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY datanode
    ADD CONSTRAINT fkey_federation_gateway_id FOREIGN KEY (federation_gateway_id) REFERENCES federation_gateway(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3026 (class 2606 OID 34305120)
-- Dependencies: 2501 218 186
-- Name: fkey_file_access_point_data_access_capability; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY file_access_point
    ADD CONSTRAINT fkey_file_access_point_data_access_capability FOREIGN KEY (data_access_capability_id) REFERENCES data_access_capability(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3027 (class 2606 OID 34305125)
-- Dependencies: 231 2687 218
-- Name: fkey_file_access_point_logical_file; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY file_access_point
    ADD CONSTRAINT fkey_file_access_point_logical_file FOREIGN KEY (logical_file_id) REFERENCES logical_file(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3028 (class 2606 OID 34305130)
-- Dependencies: 218 2738 246
-- Name: fkey_file_access_point_replica; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY file_access_point
    ADD CONSTRAINT fkey_file_access_point_replica FOREIGN KEY (replica_id) REFERENCES replica(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3029 (class 2606 OID 34305135)
-- Dependencies: 2770 218 254
-- Name: fkey_file_access_point_storage_type; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY file_access_point
    ADD CONSTRAINT fkey_file_access_point_storage_type FOREIGN KEY (storage_type_id) REFERENCES storage_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3030 (class 2606 OID 34305140)
-- Dependencies: 220 2641 219
-- Name: fkey_forcing_type; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY forcing
    ADD CONSTRAINT fkey_forcing_type FOREIGN KEY (type) REFERENCES forcing_type(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3031 (class 2606 OID 34305145)
-- Dependencies: 2697 221 234
-- Name: fkey_gateway_metadata_profile_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY gateway
    ADD CONSTRAINT fkey_gateway_metadata_profile_id FOREIGN KEY (metadata_profile_id) REFERENCES metadata_profile(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3032 (class 2606 OID 34305150)
-- Dependencies: 194 222 2538
-- Name: fkey_gateway_specific_metadata_dataset; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY gateway_specific_metadata
    ADD CONSTRAINT fkey_gateway_specific_metadata_dataset FOREIGN KEY (dataset_ref) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3033 (class 2606 OID 34305155)
-- Dependencies: 2668 225 223
-- Name: fkey_geophysical_properties_grid_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY geophysical_properties
    ADD CONSTRAINT fkey_geophysical_properties_grid_id FOREIGN KEY (grid_id) REFERENCES grid(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3034 (class 2606 OID 34305160)
-- Dependencies: 2538 223 194
-- Name: fkey_geophysical_properties_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY geophysical_properties
    ADD CONSTRAINT fkey_geophysical_properties_id FOREIGN KEY (id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3035 (class 2606 OID 34305165)
-- Dependencies: 2494 182 224
-- Name: fkey_geospatial_coordinate_axis_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY geospatial_coordinate_axis
    ADD CONSTRAINT fkey_geospatial_coordinate_axis_id FOREIGN KEY (id) REFERENCES coordinate_axis(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3052 (class 2606 OID 34305170)
-- Dependencies: 289 245 2886
-- Name: fkey_initiator_user_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY publishing_operation
    ADD CONSTRAINT fkey_initiator_user_id FOREIGN KEY (initiator) REFERENCES security."user"(id) MATCH FULL ON UPDATE SET NULL ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3036 (class 2606 OID 34305175)
-- Dependencies: 2774 256 230
-- Name: fkey_location_taxonomy_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY location
    ADD CONSTRAINT fkey_location_taxonomy_id FOREIGN KEY (taxonomy_id) REFERENCES taxonomy(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3037 (class 2606 OID 34305180)
-- Dependencies: 190 231 2516
-- Name: fkey_logical_file_data_format_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY logical_file
    ADD CONSTRAINT fkey_logical_file_data_format_id FOREIGN KEY (data_format_id) REFERENCES data_format(id) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 3038 (class 2606 OID 34305185)
-- Dependencies: 204 232 2578
-- Name: fkey_logical_file_dataset_xref_dataset_version_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY logical_file_dataset_version_xref
    ADD CONSTRAINT fkey_logical_file_dataset_xref_dataset_version_id FOREIGN KEY (dataset_version_id) REFERENCES dataset_version(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3039 (class 2606 OID 34305190)
-- Dependencies: 2687 232 231
-- Name: fkey_logical_file_dataset_xref_logical_file_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY logical_file_dataset_version_xref
    ADD CONSTRAINT fkey_logical_file_dataset_xref_logical_file_id FOREIGN KEY (logical_file_id) REFERENCES logical_file(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3040 (class 2606 OID 34305195)
-- Dependencies: 2687 233 231
-- Name: fkey_logical_file_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY logical_file_variable_xref
    ADD CONSTRAINT fkey_logical_file_id FOREIGN KEY (logical_file_id) REFERENCES logical_file(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2972 (class 2606 OID 34305200)
-- Dependencies: 2687 178 231
-- Name: fkey_logical_file_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY checksum
    ADD CONSTRAINT fkey_logical_file_id FOREIGN KEY (logical_file_id) REFERENCES logical_file(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3041 (class 2606 OID 34305205)
-- Dependencies: 2792 262 233
-- Name: fkey_logical_file_variable_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY logical_file_variable_xref
    ADD CONSTRAINT fkey_logical_file_variable_id FOREIGN KEY (variable_id) REFERENCES variable(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3043 (class 2606 OID 34305210)
-- Dependencies: 2722 241 236
-- Name: fkey_model_component_physical_domain; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY model_component
    ADD CONSTRAINT fkey_model_component_physical_domain FOREIGN KEY (physical_domain_ref) REFERENCES physical_domain(id) DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3044 (class 2606 OID 34305215)
-- Dependencies: 2704 236 236
-- Name: fkey_model_component_unconfigured_model_component_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY model_component
    ADD CONSTRAINT fkey_model_component_unconfigured_model_component_id FOREIGN KEY (unconfigured_model_component_id) REFERENCES model_component(id) DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3042 (class 2606 OID 34305220)
-- Dependencies: 2704 235 236
-- Name: fkey_model_model_component; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY model
    ADD CONSTRAINT fkey_model_model_component FOREIGN KEY (id) REFERENCES model_component(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3045 (class 2606 OID 34305225)
-- Dependencies: 2748 248 238
-- Name: fkey_note_resource; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY note
    ADD CONSTRAINT fkey_note_resource FOREIGN KEY (resource_ref) REFERENCES resource(id) DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2985 (class 2606 OID 34305230)
-- Dependencies: 2538 194 194
-- Name: fkey_parent_dataset_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset
    ADD CONSTRAINT fkey_parent_dataset_id FOREIGN KEY (parent_dataset_id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3046 (class 2606 OID 34305235)
-- Dependencies: 2748 248 239
-- Name: fkey_persistent_identifier_resource_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY persistent_identifier
    ADD CONSTRAINT fkey_persistent_identifier_resource_id FOREIGN KEY (resource_id) REFERENCES resource(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3047 (class 2606 OID 34305240)
-- Dependencies: 240 239 2718
-- Name: fkey_persistent_indentifier_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY persistent_identifier
    ADD CONSTRAINT fkey_persistent_indentifier_type_id FOREIGN KEY (type_id) REFERENCES persistent_indentifier_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3050 (class 2606 OID 34305245)
-- Dependencies: 165 2449 243
-- Name: fkey_project_activity_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fkey_project_activity_id FOREIGN KEY (id) REFERENCES activity(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 2961 (class 2606 OID 34305250)
-- Dependencies: 168 243 2732
-- Name: fkey_project_award_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY award
    ADD CONSTRAINT fkey_project_award_id FOREIGN KEY (project_id) REFERENCES project(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3051 (class 2606 OID 34305255)
-- Dependencies: 2538 243 194
-- Name: fkey_project_dataset_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fkey_project_dataset_id FOREIGN KEY (dataset_id) REFERENCES dataset(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3053 (class 2606 OID 34305260)
-- Dependencies: 204 246 2578
-- Name: fkey_replica_dataset_version; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY replica
    ADD CONSTRAINT fkey_replica_dataset_version FOREIGN KEY (dataset_version_id) REFERENCES dataset_version(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3055 (class 2606 OID 34305265)
-- Dependencies: 2748 249 248
-- Name: fkey_resource_ref; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY resource_tag_xref
    ADD CONSTRAINT fkey_resource_ref FOREIGN KEY (resource_ref) REFERENCES resource(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3054 (class 2606 OID 34305270)
-- Dependencies: 248 2755 250
-- Name: fkey_resource_type; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY resource
    ADD CONSTRAINT fkey_resource_type FOREIGN KEY (type_id) REFERENCES resource_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3057 (class 2606 OID 34305275)
-- Dependencies: 2766 253 252
-- Name: fkey_standard_name_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY standard_name
    ADD CONSTRAINT fkey_standard_name_type_id FOREIGN KEY (type_id) REFERENCES standard_name_type(id) ON UPDATE CASCADE ON DELETE RESTRICT DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3058 (class 2606 OID 34305280)
-- Dependencies: 2787 260 252
-- Name: fkey_standard_name_units_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY standard_name
    ADD CONSTRAINT fkey_standard_name_units_id FOREIGN KEY (units_id) REFERENCES unit(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE;


--
-- TOC entry 3056 (class 2606 OID 34305285)
-- Dependencies: 255 2772 249
-- Name: fkey_tag_ref; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY resource_tag_xref
    ADD CONSTRAINT fkey_tag_ref FOREIGN KEY (tag_ref) REFERENCES tag(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3059 (class 2606 OID 34305290)
-- Dependencies: 257 182 2494
-- Name: fkey_time_coordinate_axis_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY time_coordinate_axis
    ADD CONSTRAINT fkey_time_coordinate_axis_id FOREIGN KEY (id) REFERENCES coordinate_axis(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3060 (class 2606 OID 34305295)
-- Dependencies: 259 256 2774
-- Name: fkey_topic_taxonomy_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY topic
    ADD CONSTRAINT fkey_topic_taxonomy_id FOREIGN KEY (taxonomy_id) REFERENCES taxonomy(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3061 (class 2606 OID 34305300)
-- Dependencies: 262 2578 204
-- Name: fkey_variable_dataset_version_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY variable
    ADD CONSTRAINT fkey_variable_dataset_version_id FOREIGN KEY (dataset_version_id) REFERENCES dataset_version(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3007 (class 2606 OID 34305305)
-- Dependencies: 206 262 2792
-- Name: fkey_variable_dataset_version_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY dataset_version_variable_xref
    ADD CONSTRAINT fkey_variable_dataset_version_id FOREIGN KEY (variable_id) REFERENCES variable(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- TOC entry 3063 (class 2606 OID 34305310)
-- Dependencies: 2762 252 263
-- Name: fkey_variable_standard_name_xref_standard_name_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY variable_standard_name_xref
    ADD CONSTRAINT fkey_variable_standard_name_xref_standard_name_id FOREIGN KEY (standard_name_id) REFERENCES standard_name(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3064 (class 2606 OID 34305315)
-- Dependencies: 2792 262 263
-- Name: fkey_variable_standard_name_xref_variable_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY variable_standard_name_xref
    ADD CONSTRAINT fkey_variable_standard_name_xref_variable_id FOREIGN KEY (variable_id) REFERENCES variable(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3062 (class 2606 OID 34305320)
-- Dependencies: 262 2787 260
-- Name: fkey_variable_units_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY variable
    ADD CONSTRAINT fkey_variable_units_id FOREIGN KEY (units_id) REFERENCES unit(id) ON UPDATE CASCADE ON DELETE SET NULL;


--
-- TOC entry 3065 (class 2606 OID 34305325)
-- Dependencies: 2666 224 264
-- Name: fkey_z_coordinate_axis_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY z_coordinate_axis
    ADD CONSTRAINT fkey_z_coordinate_axis_id FOREIGN KEY (id) REFERENCES geospatial_coordinate_axis(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3066 (class 2606 OID 34305330)
-- Dependencies: 265 2802 264
-- Name: fkey_z_positive_type_id; Type: FK CONSTRAINT; Schema: metadata; Owner: esgcet_admin
--

ALTER TABLE ONLY z_coordinate_axis
    ADD CONSTRAINT fkey_z_positive_type_id FOREIGN KEY (z_positive_type_id) REFERENCES z_positive_type(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


SET search_path = metrics, pg_catalog;

--
-- TOC entry 3067 (class 2606 OID 34305335)
-- Dependencies: 2886 289 267
-- Name: fkey_clickstream_1; Type: FK CONSTRAINT; Schema: metrics; Owner: esgcet_admin
--

ALTER TABLE ONLY clickstream
    ADD CONSTRAINT fkey_clickstream_1 FOREIGN KEY (user_id) REFERENCES security."user"(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3068 (class 2606 OID 34305340)
-- Dependencies: 2826 272 267
-- Name: fkey_clickstream_2; Type: FK CONSTRAINT; Schema: metrics; Owner: esgcet_admin
--

ALTER TABLE ONLY clickstream
    ADD CONSTRAINT fkey_clickstream_2 FOREIGN KEY (user_agent_id) REFERENCES user_agent(id) ON UPDATE CASCADE ON DELETE SET NULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3069 (class 2606 OID 34305345)
-- Dependencies: 2819 271 272
-- Name: fkey_user_agent_operating_system_type; Type: FK CONSTRAINT; Schema: metrics; Owner: esgcet_admin
--

ALTER TABLE ONLY user_agent
    ADD CONSTRAINT fkey_user_agent_operating_system_type FOREIGN KEY (operating_system_type_id) REFERENCES operating_system_type(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3070 (class 2606 OID 34305350)
-- Dependencies: 272 273 2830
-- Name: fkey_user_agent_user_agent_type; Type: FK CONSTRAINT; Schema: metrics; Owner: esgcet_admin
--

ALTER TABLE ONLY user_agent
    ADD CONSTRAINT fkey_user_agent_user_agent_type FOREIGN KEY (user_agent_type_id) REFERENCES user_agent_type(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3071 (class 2606 OID 34305355)
-- Dependencies: 275 274 2838
-- Name: fkey_user_login; Type: FK CONSTRAINT; Schema: metrics; Owner: esgcet_admin
--

ALTER TABLE ONLY user_login
    ADD CONSTRAINT fkey_user_login FOREIGN KEY (user_login_type_id) REFERENCES user_login_type(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3072 (class 2606 OID 34305360)
-- Dependencies: 289 274 2886
-- Name: fkey_user_login_1; Type: FK CONSTRAINT; Schema: metrics; Owner: esgcet_admin
--

ALTER TABLE ONLY user_login
    ADD CONSTRAINT fkey_user_login_1 FOREIGN KEY (users_id) REFERENCES security."user"(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3073 (class 2606 OID 34305365)
-- Dependencies: 276 2886 289
-- Name: fkey_user_search_1; Type: FK CONSTRAINT; Schema: metrics; Owner: esgcet_admin
--

ALTER TABLE ONLY user_search
    ADD CONSTRAINT fkey_user_search_1 FOREIGN KEY (users_id) REFERENCES security."user"(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3074 (class 2606 OID 34305370)
-- Dependencies: 276 2846 278
-- Name: fkey_user_search_2; Type: FK CONSTRAINT; Schema: metrics; Owner: esgcet_admin
--

ALTER TABLE ONLY user_search
    ADD CONSTRAINT fkey_user_search_2 FOREIGN KEY (user_search_type_id) REFERENCES user_search_type(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3075 (class 2606 OID 34305375)
-- Dependencies: 277 2841 276
-- Name: fkey_user_search_facet_1; Type: FK CONSTRAINT; Schema: metrics; Owner: esgcet_admin
--

ALTER TABLE ONLY user_search_facet
    ADD CONSTRAINT fkey_user_search_facet_1 FOREIGN KEY (user_search_id) REFERENCES user_search(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


SET search_path = security, pg_catalog;

--
-- TOC entry 3079 (class 2606 OID 34305380)
-- Dependencies: 285 2865 284
-- Name: fkey_group_data_group_data_type_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY group_data
    ADD CONSTRAINT fkey_group_data_group_data_type_id FOREIGN KEY (group_data_type_id) REFERENCES group_data_type(id) MATCH FULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3080 (class 2606 OID 34305385)
-- Dependencies: 2856 283 286
-- Name: fkey_group_default_role_group_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY group_default_role
    ADD CONSTRAINT fkey_group_default_role_group_id FOREIGN KEY (group_id) REFERENCES "group"(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3081 (class 2606 OID 34305390)
-- Dependencies: 286 294 2903
-- Name: fkey_group_default_role_role_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY group_default_role
    ADD CONSTRAINT fkey_group_default_role_role_id FOREIGN KEY (role_id) REFERENCES role(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3077 (class 2606 OID 34305395)
-- Dependencies: 221 283 2646
-- Name: fkey_group_gateway_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY "group"
    ADD CONSTRAINT fkey_group_gateway_id FOREIGN KEY (gateway_id) REFERENCES metadata.gateway(id) DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3082 (class 2606 OID 34305400)
-- Dependencies: 287 284 2861
-- Name: fkey_group_group_data_xref_group_data_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY group_group_data_xref
    ADD CONSTRAINT fkey_group_group_data_xref_group_data_id FOREIGN KEY (group_data_id) REFERENCES group_data(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3083 (class 2606 OID 34305405)
-- Dependencies: 287 283 2856
-- Name: fkey_group_group_data_xref_group_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY group_group_data_xref
    ADD CONSTRAINT fkey_group_group_data_xref_group_id FOREIGN KEY (group_id) REFERENCES "group"(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3078 (class 2606 OID 34305410)
-- Dependencies: 293 2901 283
-- Name: fkey_group_id_principal_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY "group"
    ADD CONSTRAINT fkey_group_id_principal_id FOREIGN KEY (id) REFERENCES principal(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3084 (class 2606 OID 34305415)
-- Dependencies: 283 2856 288
-- Name: fkey_membership_group_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY membership
    ADD CONSTRAINT fkey_membership_group_id FOREIGN KEY (group_id) REFERENCES "group"(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3085 (class 2606 OID 34305420)
-- Dependencies: 294 2903 288
-- Name: fkey_membership_role_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY membership
    ADD CONSTRAINT fkey_membership_role_id FOREIGN KEY (role_id) REFERENCES role(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3086 (class 2606 OID 34305425)
-- Dependencies: 288 2913 296
-- Name: fkey_membership_status_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY membership
    ADD CONSTRAINT fkey_membership_status_id FOREIGN KEY (status_id) REFERENCES status(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3087 (class 2606 OID 34305430)
-- Dependencies: 289 2886 288
-- Name: fkey_membership_user_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY membership
    ADD CONSTRAINT fkey_membership_user_id FOREIGN KEY (user_id) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3089 (class 2606 OID 34305435)
-- Dependencies: 291 2890 292
-- Name: fkey_permission_operation_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT fkey_permission_operation_id FOREIGN KEY (operation_id) REFERENCES operation(id) MATCH FULL DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3090 (class 2606 OID 34305440)
-- Dependencies: 293 2901 292
-- Name: fkey_permission_principal_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT fkey_permission_principal_id FOREIGN KEY (principal_id) REFERENCES principal(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3091 (class 2606 OID 34305445)
-- Dependencies: 292 2748 248
-- Name: fkey_permission_resource_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT fkey_permission_resource_id FOREIGN KEY (resource_id) REFERENCES metadata.resource(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3092 (class 2606 OID 34305450)
-- Dependencies: 2890 291 295
-- Name: fkey_role_operation_xref_operation_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY role_operation_xref
    ADD CONSTRAINT fkey_role_operation_xref_operation_id FOREIGN KEY (operation_id) REFERENCES operation(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3093 (class 2606 OID 34305455)
-- Dependencies: 2903 295 294
-- Name: fkey_role_operation_xref_role_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY role_operation_xref
    ADD CONSTRAINT fkey_role_operation_xref_role_id FOREIGN KEY (role_id) REFERENCES role(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3094 (class 2606 OID 34305460)
-- Dependencies: 298 284 2861
-- Name: fkey_user_data_group_data_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY user_data
    ADD CONSTRAINT fkey_user_data_group_data_id FOREIGN KEY (group_data_id) REFERENCES group_data(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3095 (class 2606 OID 34305465)
-- Dependencies: 298 289 2886
-- Name: fkey_user_data_user_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY user_data
    ADD CONSTRAINT fkey_user_data_user_id FOREIGN KEY (user_id) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3076 (class 2606 OID 34305470)
-- Dependencies: 282 2886 289
-- Name: fkey_user_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY authorization_token
    ADD CONSTRAINT fkey_user_id FOREIGN KEY (user_id) REFERENCES "user"(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3088 (class 2606 OID 34305475)
-- Dependencies: 2901 293 289
-- Name: fkey_user_id_principal_id; Type: FK CONSTRAINT; Schema: security; Owner: esgcet_admin
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT fkey_user_id_principal_id FOREIGN KEY (id) REFERENCES principal(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


SET search_path = workspace, pg_catalog;

--
-- TOC entry 3096 (class 2606 OID 34305480)
-- Dependencies: 300 2937 302
-- Name: fkey_data_transfer_item_error; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY data_transfer_item
    ADD CONSTRAINT fkey_data_transfer_item_error FOREIGN KEY (error_id) REFERENCES data_transfer_request_error(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3097 (class 2606 OID 34305485)
-- Dependencies: 301 2930 300
-- Name: fkey_data_transfer_item_request; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY data_transfer_item
    ADD CONSTRAINT fkey_data_transfer_item_request FOREIGN KEY (request_id) REFERENCES data_transfer_request(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3098 (class 2606 OID 34305490)
-- Dependencies: 2940 300 303
-- Name: fkey_data_transfer_item_status; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY data_transfer_item
    ADD CONSTRAINT fkey_data_transfer_item_status FOREIGN KEY (status_id) REFERENCES data_transfer_request_status(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3099 (class 2606 OID 34305495)
-- Dependencies: 188 301 2508
-- Name: fkey_data_transfer_request_capability_type; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY data_transfer_request
    ADD CONSTRAINT fkey_data_transfer_request_capability_type FOREIGN KEY (data_access_capability_type_id) REFERENCES metadata.data_access_capability_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3100 (class 2606 OID 34305500)
-- Dependencies: 194 2538 301
-- Name: fkey_data_transfer_request_dataset; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY data_transfer_request
    ADD CONSTRAINT fkey_data_transfer_request_dataset FOREIGN KEY (dataset_id) REFERENCES metadata.dataset(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3101 (class 2606 OID 34305505)
-- Dependencies: 2937 302 301
-- Name: fkey_data_transfer_request_error; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY data_transfer_request
    ADD CONSTRAINT fkey_data_transfer_request_error FOREIGN KEY (error_id) REFERENCES data_transfer_request_error(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3104 (class 2606 OID 34305510)
-- Dependencies: 302 188 2508
-- Name: fkey_data_transfer_request_error_data_access_capability_type; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY data_transfer_request_error
    ADD CONSTRAINT fkey_data_transfer_request_error_data_access_capability_type FOREIGN KEY (data_access_capability_type_id) REFERENCES metadata.data_access_capability_type(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3102 (class 2606 OID 34305515)
-- Dependencies: 301 2940 303
-- Name: fkey_data_transfer_request_status; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY data_transfer_request
    ADD CONSTRAINT fkey_data_transfer_request_status FOREIGN KEY (status_id) REFERENCES data_transfer_request_status(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3103 (class 2606 OID 34305520)
-- Dependencies: 301 289 2886
-- Name: fkey_data_transfer_request_user; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY data_transfer_request
    ADD CONSTRAINT fkey_data_transfer_request_user FOREIGN KEY (user_id) REFERENCES security."user"(id) ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3105 (class 2606 OID 34305525)
-- Dependencies: 307 2956 304
-- Name: fkey_search_criteria_workspace_id; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY search_criteria
    ADD CONSTRAINT fkey_search_criteria_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspace(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3106 (class 2606 OID 34305530)
-- Dependencies: 304 2943 305
-- Name: fkey_search_facet_search_criteria_id; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY search_facet
    ADD CONSTRAINT fkey_search_facet_search_criteria_id FOREIGN KEY (search_criteria_id) REFERENCES search_criteria(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3107 (class 2606 OID 34305535)
-- Dependencies: 307 2956 306
-- Name: fkey_search_result_workspace_id; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY search_result
    ADD CONSTRAINT fkey_search_result_workspace_id FOREIGN KEY (workspace_id) REFERENCES workspace(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3108 (class 2606 OID 34305540)
-- Dependencies: 2901 307 293
-- Name: fkey_workspace_principal_id; Type: FK CONSTRAINT; Schema: workspace; Owner: esgcet_admin
--

ALTER TABLE ONLY workspace
    ADD CONSTRAINT fkey_workspace_principal_id FOREIGN KEY (principal_id) REFERENCES security.principal(id) MATCH FULL ON UPDATE CASCADE ON DELETE CASCADE DEFERRABLE INITIALLY DEFERRED;


--
-- TOC entry 3118 (class 0 OID 0)
-- Dependencies: 6
-- Name: metadata; Type: ACL; Schema: -; Owner: esgcet_admin
--

REVOKE ALL ON SCHEMA metadata FROM PUBLIC;
REVOKE ALL ON SCHEMA metadata FROM esgcet_admin;
GRANT ALL ON SCHEMA metadata TO esgcet_admin;
GRANT USAGE ON SCHEMA metadata TO esgcet_user;
GRANT USAGE ON SCHEMA metadata TO esgcet_reader;


--
-- TOC entry 3119 (class 0 OID 0)
-- Dependencies: 7
-- Name: metrics; Type: ACL; Schema: -; Owner: esgcet_admin
--

REVOKE ALL ON SCHEMA metrics FROM PUBLIC;
REVOKE ALL ON SCHEMA metrics FROM esgcet_admin;
GRANT ALL ON SCHEMA metrics TO esgcet_admin;
GRANT USAGE ON SCHEMA metrics TO esgcet_user;
GRANT USAGE ON SCHEMA metrics TO esgcet_reader;


--
-- TOC entry 3121 (class 0 OID 0)
-- Dependencies: 8
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO esgcet_admin;
GRANT USAGE ON SCHEMA public TO esgcet_user;
GRANT USAGE ON SCHEMA public TO esgcet_reader;


--
-- TOC entry 3122 (class 0 OID 0)
-- Dependencies: 9
-- Name: security; Type: ACL; Schema: -; Owner: esgcet_admin
--

REVOKE ALL ON SCHEMA security FROM PUBLIC;
REVOKE ALL ON SCHEMA security FROM esgcet_admin;
GRANT ALL ON SCHEMA security TO esgcet_admin;
GRANT USAGE ON SCHEMA security TO esgcet_user;
GRANT USAGE ON SCHEMA security TO esgcet_reader;


--
-- TOC entry 3123 (class 0 OID 0)
-- Dependencies: 10
-- Name: workspace; Type: ACL; Schema: -; Owner: esgcet_admin
--

REVOKE ALL ON SCHEMA workspace FROM PUBLIC;
REVOKE ALL ON SCHEMA workspace FROM esgcet_admin;
GRANT ALL ON SCHEMA workspace TO esgcet_admin;
GRANT USAGE ON SCHEMA workspace TO esgcet_user;
GRANT USAGE ON SCHEMA workspace TO esgcet_reader;


SET search_path = metadata, pg_catalog;

--
-- TOC entry 3125 (class 0 OID 0)
-- Dependencies: 327
-- Name: dataset_version_update_resource_timestamp(); Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON FUNCTION dataset_version_update_resource_timestamp() FROM PUBLIC;
REVOKE ALL ON FUNCTION dataset_version_update_resource_timestamp() FROM esgcet_admin;
GRANT ALL ON FUNCTION dataset_version_update_resource_timestamp() TO esgcet_admin;


--
-- TOC entry 3126 (class 0 OID 0)
-- Dependencies: 320
-- Name: persistent_object_update_timestamps(); Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON FUNCTION persistent_object_update_timestamps() FROM PUBLIC;
REVOKE ALL ON FUNCTION persistent_object_update_timestamps() FROM esgcet_admin;
GRANT ALL ON FUNCTION persistent_object_update_timestamps() TO esgcet_admin;


SET search_path = metrics, pg_catalog;

--
-- TOC entry 3127 (class 0 OID 0)
-- Dependencies: 338
-- Name: save_metrics(); Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON FUNCTION save_metrics() FROM PUBLIC;
REVOKE ALL ON FUNCTION save_metrics() FROM esgcet_admin;
GRANT ALL ON FUNCTION save_metrics() TO esgcet_admin;


SET search_path = metadata, pg_catalog;

--
-- TOC entry 3128 (class 0 OID 0)
-- Dependencies: 165
-- Name: activity; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE activity FROM PUBLIC;
REVOKE ALL ON TABLE activity FROM esgcet_admin;
GRANT ALL ON TABLE activity TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE activity TO esgcet_user;
GRANT SELECT ON TABLE activity TO esgcet_reader;


--
-- TOC entry 3129 (class 0 OID 0)
-- Dependencies: 166
-- Name: activity_link; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE activity_link FROM PUBLIC;
REVOKE ALL ON TABLE activity_link FROM esgcet_admin;
GRANT ALL ON TABLE activity_link TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE activity_link TO esgcet_user;
GRANT SELECT ON TABLE activity_link TO esgcet_reader;


--
-- TOC entry 3130 (class 0 OID 0)
-- Dependencies: 167
-- Name: activity_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE activity_type FROM PUBLIC;
REVOKE ALL ON TABLE activity_type FROM esgcet_admin;
GRANT ALL ON TABLE activity_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE activity_type TO esgcet_user;
GRANT SELECT ON TABLE activity_type TO esgcet_reader;


--
-- TOC entry 3131 (class 0 OID 0)
-- Dependencies: 168
-- Name: award; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE award FROM PUBLIC;
REVOKE ALL ON TABLE award FROM esgcet_admin;
GRANT ALL ON TABLE award TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE award TO esgcet_user;
GRANT SELECT ON TABLE award TO esgcet_reader;


--
-- TOC entry 3132 (class 0 OID 0)
-- Dependencies: 169
-- Name: broadcast_message; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE broadcast_message FROM PUBLIC;
REVOKE ALL ON TABLE broadcast_message FROM esgcet_admin;
GRANT ALL ON TABLE broadcast_message TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE broadcast_message TO esgcet_user;
GRANT SELECT ON TABLE broadcast_message TO esgcet_reader;


--
-- TOC entry 3133 (class 0 OID 0)
-- Dependencies: 170
-- Name: broadcast_message_sequence; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON SEQUENCE broadcast_message_sequence FROM PUBLIC;
REVOKE ALL ON SEQUENCE broadcast_message_sequence FROM esgcet_admin;
GRANT ALL ON SEQUENCE broadcast_message_sequence TO esgcet_admin;
GRANT SELECT,UPDATE ON SEQUENCE broadcast_message_sequence TO esgcet_user;
GRANT SELECT ON SEQUENCE broadcast_message_sequence TO esgcet_reader;


--
-- TOC entry 3134 (class 0 OID 0)
-- Dependencies: 171
-- Name: cadis_descriptive_metadata; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE cadis_descriptive_metadata FROM PUBLIC;
REVOKE ALL ON TABLE cadis_descriptive_metadata FROM esgcet_admin;
GRANT ALL ON TABLE cadis_descriptive_metadata TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE cadis_descriptive_metadata TO esgcet_user;
GRANT SELECT ON TABLE cadis_descriptive_metadata TO esgcet_reader;


--
-- TOC entry 3135 (class 0 OID 0)
-- Dependencies: 172
-- Name: cadis_descriptive_metadata_cadis_resolution_type_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE cadis_descriptive_metadata_cadis_resolution_type_xref FROM PUBLIC;
REVOKE ALL ON TABLE cadis_descriptive_metadata_cadis_resolution_type_xref FROM esgcet_admin;
GRANT ALL ON TABLE cadis_descriptive_metadata_cadis_resolution_type_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE cadis_descriptive_metadata_cadis_resolution_type_xref TO esgcet_user;
GRANT SELECT ON TABLE cadis_descriptive_metadata_cadis_resolution_type_xref TO esgcet_reader;


--
-- TOC entry 3136 (class 0 OID 0)
-- Dependencies: 173
-- Name: cadis_project; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE cadis_project FROM PUBLIC;
REVOKE ALL ON TABLE cadis_project FROM esgcet_admin;
GRANT ALL ON TABLE cadis_project TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE cadis_project TO esgcet_user;
GRANT SELECT ON TABLE cadis_project TO esgcet_reader;


--
-- TOC entry 3137 (class 0 OID 0)
-- Dependencies: 174
-- Name: cadis_project_project_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE cadis_project_project_xref FROM PUBLIC;
REVOKE ALL ON TABLE cadis_project_project_xref FROM esgcet_admin;
GRANT ALL ON TABLE cadis_project_project_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE cadis_project_project_xref TO esgcet_user;
GRANT SELECT ON TABLE cadis_project_project_xref TO esgcet_reader;


--
-- TOC entry 3138 (class 0 OID 0)
-- Dependencies: 175
-- Name: cadis_project_typed_contact; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE cadis_project_typed_contact FROM PUBLIC;
REVOKE ALL ON TABLE cadis_project_typed_contact FROM esgcet_admin;
GRANT ALL ON TABLE cadis_project_typed_contact TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE cadis_project_typed_contact TO esgcet_user;
GRANT SELECT ON TABLE cadis_project_typed_contact TO esgcet_reader;


--
-- TOC entry 3139 (class 0 OID 0)
-- Dependencies: 176
-- Name: cadis_resolution_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE cadis_resolution_type FROM PUBLIC;
REVOKE ALL ON TABLE cadis_resolution_type FROM esgcet_admin;
GRANT ALL ON TABLE cadis_resolution_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE cadis_resolution_type TO esgcet_user;
GRANT SELECT ON TABLE cadis_resolution_type TO esgcet_reader;


--
-- TOC entry 3140 (class 0 OID 0)
-- Dependencies: 177
-- Name: campaign; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE campaign FROM PUBLIC;
REVOKE ALL ON TABLE campaign FROM esgcet_admin;
GRANT ALL ON TABLE campaign TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE campaign TO esgcet_user;
GRANT SELECT ON TABLE campaign TO esgcet_reader;


--
-- TOC entry 3141 (class 0 OID 0)
-- Dependencies: 178
-- Name: checksum; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE checksum FROM PUBLIC;
REVOKE ALL ON TABLE checksum FROM esgcet_admin;
GRANT ALL ON TABLE checksum TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE checksum TO esgcet_user;
GRANT SELECT ON TABLE checksum TO esgcet_reader;


--
-- TOC entry 3142 (class 0 OID 0)
-- Dependencies: 179
-- Name: collection_note; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE collection_note FROM PUBLIC;
REVOKE ALL ON TABLE collection_note FROM esgcet_admin;
GRANT ALL ON TABLE collection_note TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE collection_note TO esgcet_user;
GRANT SELECT ON TABLE collection_note TO esgcet_reader;


--
-- TOC entry 3143 (class 0 OID 0)
-- Dependencies: 180
-- Name: contact; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE contact FROM PUBLIC;
REVOKE ALL ON TABLE contact FROM esgcet_admin;
GRANT ALL ON TABLE contact TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE contact TO esgcet_user;
GRANT SELECT ON TABLE contact TO esgcet_reader;


--
-- TOC entry 3144 (class 0 OID 0)
-- Dependencies: 181
-- Name: contact_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE contact_type FROM PUBLIC;
REVOKE ALL ON TABLE contact_type FROM esgcet_admin;
GRANT ALL ON TABLE contact_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE contact_type TO esgcet_user;
GRANT SELECT ON TABLE contact_type TO esgcet_reader;


--
-- TOC entry 3145 (class 0 OID 0)
-- Dependencies: 182
-- Name: coordinate_axis; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE coordinate_axis FROM PUBLIC;
REVOKE ALL ON TABLE coordinate_axis FROM esgcet_admin;
GRANT ALL ON TABLE coordinate_axis TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE coordinate_axis TO esgcet_user;
GRANT SELECT ON TABLE coordinate_axis TO esgcet_reader;


--
-- TOC entry 3146 (class 0 OID 0)
-- Dependencies: 183
-- Name: coordinate_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE coordinate_type FROM PUBLIC;
REVOKE ALL ON TABLE coordinate_type FROM esgcet_admin;
GRANT ALL ON TABLE coordinate_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE coordinate_type TO esgcet_user;
GRANT SELECT ON TABLE coordinate_type TO esgcet_reader;


--
-- TOC entry 3147 (class 0 OID 0)
-- Dependencies: 184
-- Name: data_access_application; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_access_application FROM PUBLIC;
REVOKE ALL ON TABLE data_access_application FROM esgcet_admin;
GRANT ALL ON TABLE data_access_application TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_access_application TO esgcet_user;
GRANT SELECT ON TABLE data_access_application TO esgcet_reader;


--
-- TOC entry 3148 (class 0 OID 0)
-- Dependencies: 185
-- Name: data_access_application_protocol_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_access_application_protocol_xref FROM PUBLIC;
REVOKE ALL ON TABLE data_access_application_protocol_xref FROM esgcet_admin;
GRANT ALL ON TABLE data_access_application_protocol_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_access_application_protocol_xref TO esgcet_user;
GRANT SELECT ON TABLE data_access_application_protocol_xref TO esgcet_reader;


--
-- TOC entry 3149 (class 0 OID 0)
-- Dependencies: 186
-- Name: data_access_capability; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_access_capability FROM PUBLIC;
REVOKE ALL ON TABLE data_access_capability FROM esgcet_admin;
GRANT ALL ON TABLE data_access_capability TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_access_capability TO esgcet_user;
GRANT SELECT ON TABLE data_access_capability TO esgcet_reader;


--
-- TOC entry 3150 (class 0 OID 0)
-- Dependencies: 187
-- Name: data_access_capability_application_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_access_capability_application_xref FROM PUBLIC;
REVOKE ALL ON TABLE data_access_capability_application_xref FROM esgcet_admin;
GRANT ALL ON TABLE data_access_capability_application_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_access_capability_application_xref TO esgcet_user;
GRANT SELECT ON TABLE data_access_capability_application_xref TO esgcet_reader;


--
-- TOC entry 3151 (class 0 OID 0)
-- Dependencies: 188
-- Name: data_access_capability_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_access_capability_type FROM PUBLIC;
REVOKE ALL ON TABLE data_access_capability_type FROM esgcet_admin;
GRANT ALL ON TABLE data_access_capability_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_access_capability_type TO esgcet_user;
GRANT SELECT ON TABLE data_access_capability_type TO esgcet_reader;


--
-- TOC entry 3152 (class 0 OID 0)
-- Dependencies: 189
-- Name: data_access_protocol; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_access_protocol FROM PUBLIC;
REVOKE ALL ON TABLE data_access_protocol FROM esgcet_admin;
GRANT ALL ON TABLE data_access_protocol TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_access_protocol TO esgcet_user;
GRANT SELECT ON TABLE data_access_protocol TO esgcet_reader;


--
-- TOC entry 3153 (class 0 OID 0)
-- Dependencies: 190
-- Name: data_format; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_format FROM PUBLIC;
REVOKE ALL ON TABLE data_format FROM esgcet_admin;
GRANT ALL ON TABLE data_format TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_format TO esgcet_user;
GRANT SELECT ON TABLE data_format TO esgcet_reader;


--
-- TOC entry 3154 (class 0 OID 0)
-- Dependencies: 191
-- Name: data_product_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_product_type FROM PUBLIC;
REVOKE ALL ON TABLE data_product_type FROM esgcet_admin;
GRANT ALL ON TABLE data_product_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_product_type TO esgcet_user;
GRANT SELECT ON TABLE data_product_type TO esgcet_reader;


--
-- TOC entry 3155 (class 0 OID 0)
-- Dependencies: 192
-- Name: data_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_type FROM PUBLIC;
REVOKE ALL ON TABLE data_type FROM esgcet_admin;
GRANT ALL ON TABLE data_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_type TO esgcet_user;
GRANT SELECT ON TABLE data_type TO esgcet_reader;


--
-- TOC entry 3156 (class 0 OID 0)
-- Dependencies: 193
-- Name: datanode; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE datanode FROM PUBLIC;
REVOKE ALL ON TABLE datanode FROM esgcet_admin;
GRANT ALL ON TABLE datanode TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE datanode TO esgcet_user;
GRANT SELECT ON TABLE datanode TO esgcet_reader;


--
-- TOC entry 3157 (class 0 OID 0)
-- Dependencies: 194
-- Name: dataset; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset FROM PUBLIC;
REVOKE ALL ON TABLE dataset FROM esgcet_admin;
GRANT ALL ON TABLE dataset TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset TO esgcet_user;
GRANT SELECT ON TABLE dataset TO esgcet_reader;


--
-- TOC entry 3158 (class 0 OID 0)
-- Dependencies: 195
-- Name: dataset_access_point; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_access_point FROM PUBLIC;
REVOKE ALL ON TABLE dataset_access_point FROM esgcet_admin;
GRANT ALL ON TABLE dataset_access_point TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_access_point TO esgcet_user;
GRANT SELECT ON TABLE dataset_access_point TO esgcet_reader;


--
-- TOC entry 3159 (class 0 OID 0)
-- Dependencies: 196
-- Name: dataset_activity_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_activity_xref FROM PUBLIC;
REVOKE ALL ON TABLE dataset_activity_xref FROM esgcet_admin;
GRANT ALL ON TABLE dataset_activity_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_activity_xref TO esgcet_user;
GRANT SELECT ON TABLE dataset_activity_xref TO esgcet_reader;


--
-- TOC entry 3160 (class 0 OID 0)
-- Dependencies: 197
-- Name: dataset_citation; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_citation FROM PUBLIC;
REVOKE ALL ON TABLE dataset_citation FROM esgcet_admin;
GRANT ALL ON TABLE dataset_citation TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_citation TO esgcet_user;
GRANT SELECT ON TABLE dataset_citation TO esgcet_reader;


--
-- TOC entry 3161 (class 0 OID 0)
-- Dependencies: 198
-- Name: dataset_contact_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_contact_xref FROM PUBLIC;
REVOKE ALL ON TABLE dataset_contact_xref FROM esgcet_admin;
GRANT ALL ON TABLE dataset_contact_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_contact_xref TO esgcet_user;
GRANT SELECT ON TABLE dataset_contact_xref TO esgcet_reader;


--
-- TOC entry 3162 (class 0 OID 0)
-- Dependencies: 199
-- Name: dataset_data_format_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_data_format_xref FROM PUBLIC;
REVOKE ALL ON TABLE dataset_data_format_xref FROM esgcet_admin;
GRANT ALL ON TABLE dataset_data_format_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_data_format_xref TO esgcet_user;
GRANT SELECT ON TABLE dataset_data_format_xref TO esgcet_reader;


--
-- TOC entry 3163 (class 0 OID 0)
-- Dependencies: 200
-- Name: dataset_progress; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_progress FROM PUBLIC;
REVOKE ALL ON TABLE dataset_progress FROM esgcet_admin;
GRANT ALL ON TABLE dataset_progress TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_progress TO esgcet_user;
GRANT SELECT ON TABLE dataset_progress TO esgcet_reader;


--
-- TOC entry 3164 (class 0 OID 0)
-- Dependencies: 201
-- Name: dataset_restriction; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_restriction FROM PUBLIC;
REVOKE ALL ON TABLE dataset_restriction FROM esgcet_admin;
GRANT ALL ON TABLE dataset_restriction TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_restriction TO esgcet_user;
GRANT SELECT ON TABLE dataset_restriction TO esgcet_reader;


--
-- TOC entry 3165 (class 0 OID 0)
-- Dependencies: 202
-- Name: dataset_topic_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_topic_xref FROM PUBLIC;
REVOKE ALL ON TABLE dataset_topic_xref FROM esgcet_admin;
GRANT ALL ON TABLE dataset_topic_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_topic_xref TO esgcet_user;
GRANT SELECT ON TABLE dataset_topic_xref TO esgcet_reader;


--
-- TOC entry 3166 (class 0 OID 0)
-- Dependencies: 203
-- Name: dataset_typed_contact; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_typed_contact FROM PUBLIC;
REVOKE ALL ON TABLE dataset_typed_contact FROM esgcet_admin;
GRANT ALL ON TABLE dataset_typed_contact TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_typed_contact TO esgcet_user;
GRANT SELECT ON TABLE dataset_typed_contact TO esgcet_reader;


--
-- TOC entry 3167 (class 0 OID 0)
-- Dependencies: 204
-- Name: dataset_version; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_version FROM PUBLIC;
REVOKE ALL ON TABLE dataset_version FROM esgcet_admin;
GRANT ALL ON TABLE dataset_version TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_version TO esgcet_user;
GRANT SELECT ON TABLE dataset_version TO esgcet_reader;


--
-- TOC entry 3168 (class 0 OID 0)
-- Dependencies: 206
-- Name: dataset_version_variable_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE dataset_version_variable_xref FROM PUBLIC;
REVOKE ALL ON TABLE dataset_version_variable_xref FROM esgcet_admin;
GRANT ALL ON TABLE dataset_version_variable_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE dataset_version_variable_xref TO esgcet_user;
GRANT SELECT ON TABLE dataset_version_variable_xref TO esgcet_reader;


--
-- TOC entry 3169 (class 0 OID 0)
-- Dependencies: 207
-- Name: descriptive_metadata; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE descriptive_metadata FROM PUBLIC;
REVOKE ALL ON TABLE descriptive_metadata FROM esgcet_admin;
GRANT ALL ON TABLE descriptive_metadata TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE descriptive_metadata TO esgcet_user;
GRANT SELECT ON TABLE descriptive_metadata TO esgcet_reader;


--
-- TOC entry 3170 (class 0 OID 0)
-- Dependencies: 208
-- Name: descriptive_metadata_data_type_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE descriptive_metadata_data_type_xref FROM PUBLIC;
REVOKE ALL ON TABLE descriptive_metadata_data_type_xref FROM esgcet_admin;
GRANT ALL ON TABLE descriptive_metadata_data_type_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE descriptive_metadata_data_type_xref TO esgcet_user;
GRANT SELECT ON TABLE descriptive_metadata_data_type_xref TO esgcet_reader;


--
-- TOC entry 3171 (class 0 OID 0)
-- Dependencies: 209
-- Name: descriptive_metadata_instrument_type_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE descriptive_metadata_instrument_type_xref FROM PUBLIC;
REVOKE ALL ON TABLE descriptive_metadata_instrument_type_xref FROM esgcet_admin;
GRANT ALL ON TABLE descriptive_metadata_instrument_type_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE descriptive_metadata_instrument_type_xref TO esgcet_user;
GRANT SELECT ON TABLE descriptive_metadata_instrument_type_xref TO esgcet_reader;


--
-- TOC entry 3172 (class 0 OID 0)
-- Dependencies: 210
-- Name: descriptive_metadata_link; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE descriptive_metadata_link FROM PUBLIC;
REVOKE ALL ON TABLE descriptive_metadata_link FROM esgcet_admin;
GRANT ALL ON TABLE descriptive_metadata_link TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE descriptive_metadata_link TO esgcet_user;
GRANT SELECT ON TABLE descriptive_metadata_link TO esgcet_reader;


--
-- TOC entry 3173 (class 0 OID 0)
-- Dependencies: 211
-- Name: descriptive_metadata_location_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE descriptive_metadata_location_xref FROM PUBLIC;
REVOKE ALL ON TABLE descriptive_metadata_location_xref FROM esgcet_admin;
GRANT ALL ON TABLE descriptive_metadata_location_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE descriptive_metadata_location_xref TO esgcet_user;
GRANT SELECT ON TABLE descriptive_metadata_location_xref TO esgcet_reader;


--
-- TOC entry 3174 (class 0 OID 0)
-- Dependencies: 212
-- Name: descriptive_metadata_platform_type_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE descriptive_metadata_platform_type_xref FROM PUBLIC;
REVOKE ALL ON TABLE descriptive_metadata_platform_type_xref FROM esgcet_admin;
GRANT ALL ON TABLE descriptive_metadata_platform_type_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE descriptive_metadata_platform_type_xref TO esgcet_user;
GRANT SELECT ON TABLE descriptive_metadata_platform_type_xref TO esgcet_reader;


--
-- TOC entry 3175 (class 0 OID 0)
-- Dependencies: 213
-- Name: descriptive_metadata_time_frequency_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE descriptive_metadata_time_frequency_xref FROM PUBLIC;
REVOKE ALL ON TABLE descriptive_metadata_time_frequency_xref FROM esgcet_admin;
GRANT ALL ON TABLE descriptive_metadata_time_frequency_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE descriptive_metadata_time_frequency_xref TO esgcet_user;
GRANT SELECT ON TABLE descriptive_metadata_time_frequency_xref TO esgcet_reader;


--
-- TOC entry 3176 (class 0 OID 0)
-- Dependencies: 214
-- Name: ensemble; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE ensemble FROM PUBLIC;
REVOKE ALL ON TABLE ensemble FROM esgcet_admin;
GRANT ALL ON TABLE ensemble TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE ensemble TO esgcet_user;
GRANT SELECT ON TABLE ensemble TO esgcet_reader;


--
-- TOC entry 3177 (class 0 OID 0)
-- Dependencies: 215
-- Name: events; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE events FROM PUBLIC;
REVOKE ALL ON TABLE events FROM esgcet_admin;
GRANT ALL ON TABLE events TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE events TO esgcet_user;
GRANT SELECT ON TABLE events TO esgcet_reader;


--
-- TOC entry 3178 (class 0 OID 0)
-- Dependencies: 216
-- Name: experiment; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE experiment FROM PUBLIC;
REVOKE ALL ON TABLE experiment FROM esgcet_admin;
GRANT ALL ON TABLE experiment TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE experiment TO esgcet_user;
GRANT SELECT ON TABLE experiment TO esgcet_reader;


--
-- TOC entry 3179 (class 0 OID 0)
-- Dependencies: 217
-- Name: federation_gateway; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE federation_gateway FROM PUBLIC;
REVOKE ALL ON TABLE federation_gateway FROM esgcet_admin;
GRANT ALL ON TABLE federation_gateway TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE federation_gateway TO esgcet_user;
GRANT SELECT ON TABLE federation_gateway TO esgcet_reader;


--
-- TOC entry 3180 (class 0 OID 0)
-- Dependencies: 218
-- Name: file_access_point; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE file_access_point FROM PUBLIC;
REVOKE ALL ON TABLE file_access_point FROM esgcet_admin;
GRANT ALL ON TABLE file_access_point TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE file_access_point TO esgcet_user;
GRANT SELECT ON TABLE file_access_point TO esgcet_reader;


--
-- TOC entry 3181 (class 0 OID 0)
-- Dependencies: 219
-- Name: forcing; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE forcing FROM PUBLIC;
REVOKE ALL ON TABLE forcing FROM esgcet_admin;
GRANT ALL ON TABLE forcing TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE forcing TO esgcet_user;
GRANT SELECT ON TABLE forcing TO esgcet_reader;


--
-- TOC entry 3182 (class 0 OID 0)
-- Dependencies: 220
-- Name: forcing_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE forcing_type FROM PUBLIC;
REVOKE ALL ON TABLE forcing_type FROM esgcet_admin;
GRANT ALL ON TABLE forcing_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE forcing_type TO esgcet_user;
GRANT SELECT ON TABLE forcing_type TO esgcet_reader;


--
-- TOC entry 3183 (class 0 OID 0)
-- Dependencies: 221
-- Name: gateway; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE gateway FROM PUBLIC;
REVOKE ALL ON TABLE gateway FROM esgcet_admin;
GRANT ALL ON TABLE gateway TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE gateway TO esgcet_user;
GRANT SELECT ON TABLE gateway TO esgcet_reader;


--
-- TOC entry 3184 (class 0 OID 0)
-- Dependencies: 222
-- Name: gateway_specific_metadata; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE gateway_specific_metadata FROM PUBLIC;
REVOKE ALL ON TABLE gateway_specific_metadata FROM esgcet_admin;
GRANT ALL ON TABLE gateway_specific_metadata TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE gateway_specific_metadata TO esgcet_user;
GRANT SELECT ON TABLE gateway_specific_metadata TO esgcet_reader;


--
-- TOC entry 3185 (class 0 OID 0)
-- Dependencies: 223
-- Name: geophysical_properties; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE geophysical_properties FROM PUBLIC;
REVOKE ALL ON TABLE geophysical_properties FROM esgcet_admin;
GRANT ALL ON TABLE geophysical_properties TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE geophysical_properties TO esgcet_user;
GRANT SELECT ON TABLE geophysical_properties TO esgcet_reader;


--
-- TOC entry 3186 (class 0 OID 0)
-- Dependencies: 224
-- Name: geospatial_coordinate_axis; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE geospatial_coordinate_axis FROM PUBLIC;
REVOKE ALL ON TABLE geospatial_coordinate_axis FROM esgcet_admin;
GRANT ALL ON TABLE geospatial_coordinate_axis TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE geospatial_coordinate_axis TO esgcet_user;
GRANT SELECT ON TABLE geospatial_coordinate_axis TO esgcet_reader;


--
-- TOC entry 3188 (class 0 OID 0)
-- Dependencies: 225
-- Name: grid; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE grid FROM PUBLIC;
REVOKE ALL ON TABLE grid FROM esgcet_admin;
GRANT ALL ON TABLE grid TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE grid TO esgcet_user;
GRANT SELECT ON TABLE grid TO esgcet_reader;


--
-- TOC entry 3189 (class 0 OID 0)
-- Dependencies: 226
-- Name: institution; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE institution FROM PUBLIC;
REVOKE ALL ON TABLE institution FROM esgcet_admin;
GRANT ALL ON TABLE institution TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE institution TO esgcet_user;
GRANT SELECT ON TABLE institution TO esgcet_reader;


--
-- TOC entry 3190 (class 0 OID 0)
-- Dependencies: 227
-- Name: instrument_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE instrument_type FROM PUBLIC;
REVOKE ALL ON TABLE instrument_type FROM esgcet_admin;
GRANT ALL ON TABLE instrument_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE instrument_type TO esgcet_user;
GRANT SELECT ON TABLE instrument_type TO esgcet_reader;


--
-- TOC entry 3191 (class 0 OID 0)
-- Dependencies: 228
-- Name: license; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE license FROM PUBLIC;
REVOKE ALL ON TABLE license FROM esgcet_admin;
GRANT ALL ON TABLE license TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE license TO esgcet_user;
GRANT SELECT ON TABLE license TO esgcet_reader;


--
-- TOC entry 3192 (class 0 OID 0)
-- Dependencies: 229
-- Name: link_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE link_type FROM PUBLIC;
REVOKE ALL ON TABLE link_type FROM esgcet_admin;
GRANT ALL ON TABLE link_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE link_type TO esgcet_user;
GRANT SELECT ON TABLE link_type TO esgcet_reader;


--
-- TOC entry 3193 (class 0 OID 0)
-- Dependencies: 230
-- Name: location; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE location FROM PUBLIC;
REVOKE ALL ON TABLE location FROM esgcet_admin;
GRANT ALL ON TABLE location TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE location TO esgcet_user;
GRANT SELECT ON TABLE location TO esgcet_reader;


--
-- TOC entry 3194 (class 0 OID 0)
-- Dependencies: 231
-- Name: logical_file; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE logical_file FROM PUBLIC;
REVOKE ALL ON TABLE logical_file FROM esgcet_admin;
GRANT ALL ON TABLE logical_file TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE logical_file TO esgcet_user;
GRANT SELECT ON TABLE logical_file TO esgcet_reader;


--
-- TOC entry 3195 (class 0 OID 0)
-- Dependencies: 232
-- Name: logical_file_dataset_version_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE logical_file_dataset_version_xref FROM PUBLIC;
REVOKE ALL ON TABLE logical_file_dataset_version_xref FROM esgcet_admin;
GRANT ALL ON TABLE logical_file_dataset_version_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE logical_file_dataset_version_xref TO esgcet_user;
GRANT SELECT ON TABLE logical_file_dataset_version_xref TO esgcet_reader;


--
-- TOC entry 3196 (class 0 OID 0)
-- Dependencies: 233
-- Name: logical_file_variable_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE logical_file_variable_xref FROM PUBLIC;
REVOKE ALL ON TABLE logical_file_variable_xref FROM esgcet_admin;
GRANT ALL ON TABLE logical_file_variable_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE logical_file_variable_xref TO esgcet_user;
GRANT SELECT ON TABLE logical_file_variable_xref TO esgcet_reader;


--
-- TOC entry 3197 (class 0 OID 0)
-- Dependencies: 234
-- Name: metadata_profile; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE metadata_profile FROM PUBLIC;
REVOKE ALL ON TABLE metadata_profile FROM esgcet_admin;
GRANT ALL ON TABLE metadata_profile TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE metadata_profile TO esgcet_user;
GRANT SELECT ON TABLE metadata_profile TO esgcet_reader;


--
-- TOC entry 3198 (class 0 OID 0)
-- Dependencies: 235
-- Name: model; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE model FROM PUBLIC;
REVOKE ALL ON TABLE model FROM esgcet_admin;
GRANT ALL ON TABLE model TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE model TO esgcet_user;
GRANT SELECT ON TABLE model TO esgcet_reader;


--
-- TOC entry 3199 (class 0 OID 0)
-- Dependencies: 236
-- Name: model_component; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE model_component FROM PUBLIC;
REVOKE ALL ON TABLE model_component FROM esgcet_admin;
GRANT ALL ON TABLE model_component TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE model_component TO esgcet_user;
GRANT SELECT ON TABLE model_component TO esgcet_reader;


--
-- TOC entry 3200 (class 0 OID 0)
-- Dependencies: 237
-- Name: model_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE model_type FROM PUBLIC;
REVOKE ALL ON TABLE model_type FROM esgcet_admin;
GRANT ALL ON TABLE model_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE model_type TO esgcet_user;
GRANT SELECT ON TABLE model_type TO esgcet_reader;


--
-- TOC entry 3201 (class 0 OID 0)
-- Dependencies: 238
-- Name: note; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE note FROM PUBLIC;
REVOKE ALL ON TABLE note FROM esgcet_admin;
GRANT ALL ON TABLE note TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE note TO esgcet_user;
GRANT SELECT ON TABLE note TO esgcet_reader;


--
-- TOC entry 3202 (class 0 OID 0)
-- Dependencies: 239
-- Name: persistent_identifier; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE persistent_identifier FROM PUBLIC;
REVOKE ALL ON TABLE persistent_identifier FROM esgcet_admin;
GRANT ALL ON TABLE persistent_identifier TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE persistent_identifier TO esgcet_user;
GRANT SELECT ON TABLE persistent_identifier TO esgcet_reader;


--
-- TOC entry 3203 (class 0 OID 0)
-- Dependencies: 240
-- Name: persistent_indentifier_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE persistent_indentifier_type FROM PUBLIC;
REVOKE ALL ON TABLE persistent_indentifier_type FROM esgcet_admin;
GRANT ALL ON TABLE persistent_indentifier_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE persistent_indentifier_type TO esgcet_user;
GRANT SELECT ON TABLE persistent_indentifier_type TO esgcet_reader;


--
-- TOC entry 3204 (class 0 OID 0)
-- Dependencies: 241
-- Name: physical_domain; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE physical_domain FROM PUBLIC;
REVOKE ALL ON TABLE physical_domain FROM esgcet_admin;
GRANT ALL ON TABLE physical_domain TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE physical_domain TO esgcet_user;
GRANT SELECT ON TABLE physical_domain TO esgcet_reader;


--
-- TOC entry 3205 (class 0 OID 0)
-- Dependencies: 242
-- Name: platform_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE platform_type FROM PUBLIC;
REVOKE ALL ON TABLE platform_type FROM esgcet_admin;
GRANT ALL ON TABLE platform_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE platform_type TO esgcet_user;
GRANT SELECT ON TABLE platform_type TO esgcet_reader;


--
-- TOC entry 3206 (class 0 OID 0)
-- Dependencies: 243
-- Name: project; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE project FROM PUBLIC;
REVOKE ALL ON TABLE project FROM esgcet_admin;
GRANT ALL ON TABLE project TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE project TO esgcet_user;
GRANT SELECT ON TABLE project TO esgcet_reader;


--
-- TOC entry 3207 (class 0 OID 0)
-- Dependencies: 244
-- Name: published_state; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE published_state FROM PUBLIC;
REVOKE ALL ON TABLE published_state FROM esgcet_admin;
GRANT ALL ON TABLE published_state TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE published_state TO esgcet_user;
GRANT SELECT ON TABLE published_state TO esgcet_reader;


--
-- TOC entry 3208 (class 0 OID 0)
-- Dependencies: 245
-- Name: publishing_operation; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE publishing_operation FROM PUBLIC;
REVOKE ALL ON TABLE publishing_operation FROM esgcet_admin;
GRANT ALL ON TABLE publishing_operation TO esgcet_admin;
GRANT ALL ON TABLE publishing_operation TO esgcet_user;


--
-- TOC entry 3209 (class 0 OID 0)
-- Dependencies: 246
-- Name: replica; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE replica FROM PUBLIC;
REVOKE ALL ON TABLE replica FROM esgcet_admin;
GRANT ALL ON TABLE replica TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE replica TO esgcet_user;
GRANT SELECT ON TABLE replica TO esgcet_reader;


--
-- TOC entry 3210 (class 0 OID 0)
-- Dependencies: 247
-- Name: resolution_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE resolution_type FROM PUBLIC;
REVOKE ALL ON TABLE resolution_type FROM esgcet_admin;
GRANT ALL ON TABLE resolution_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE resolution_type TO esgcet_user;
GRANT SELECT ON TABLE resolution_type TO esgcet_reader;


--
-- TOC entry 3211 (class 0 OID 0)
-- Dependencies: 248
-- Name: resource; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE resource FROM PUBLIC;
REVOKE ALL ON TABLE resource FROM esgcet_admin;
GRANT ALL ON TABLE resource TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE resource TO esgcet_user;
GRANT SELECT ON TABLE resource TO esgcet_reader;


--
-- TOC entry 3212 (class 0 OID 0)
-- Dependencies: 249
-- Name: resource_tag_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE resource_tag_xref FROM PUBLIC;
REVOKE ALL ON TABLE resource_tag_xref FROM esgcet_admin;
GRANT ALL ON TABLE resource_tag_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE resource_tag_xref TO esgcet_user;
GRANT SELECT ON TABLE resource_tag_xref TO esgcet_reader;


--
-- TOC entry 3213 (class 0 OID 0)
-- Dependencies: 250
-- Name: resource_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE resource_type FROM PUBLIC;
REVOKE ALL ON TABLE resource_type FROM esgcet_admin;
GRANT ALL ON TABLE resource_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE resource_type TO esgcet_user;
GRANT SELECT ON TABLE resource_type TO esgcet_reader;


--
-- TOC entry 3214 (class 0 OID 0)
-- Dependencies: 251
-- Name: software_properties; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE software_properties FROM PUBLIC;
REVOKE ALL ON TABLE software_properties FROM esgcet_admin;
GRANT ALL ON TABLE software_properties TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE software_properties TO esgcet_user;
GRANT SELECT ON TABLE software_properties TO esgcet_reader;


--
-- TOC entry 3215 (class 0 OID 0)
-- Dependencies: 252
-- Name: standard_name; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE standard_name FROM PUBLIC;
REVOKE ALL ON TABLE standard_name FROM esgcet_admin;
GRANT ALL ON TABLE standard_name TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE standard_name TO esgcet_user;
GRANT SELECT ON TABLE standard_name TO esgcet_reader;


--
-- TOC entry 3216 (class 0 OID 0)
-- Dependencies: 253
-- Name: standard_name_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE standard_name_type FROM PUBLIC;
REVOKE ALL ON TABLE standard_name_type FROM esgcet_admin;
GRANT ALL ON TABLE standard_name_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE standard_name_type TO esgcet_user;
GRANT SELECT ON TABLE standard_name_type TO esgcet_reader;


--
-- TOC entry 3217 (class 0 OID 0)
-- Dependencies: 254
-- Name: storage_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE storage_type FROM PUBLIC;
REVOKE ALL ON TABLE storage_type FROM esgcet_admin;
GRANT ALL ON TABLE storage_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE storage_type TO esgcet_user;
GRANT SELECT ON TABLE storage_type TO esgcet_reader;


--
-- TOC entry 3218 (class 0 OID 0)
-- Dependencies: 255
-- Name: tag; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE tag FROM PUBLIC;
REVOKE ALL ON TABLE tag FROM esgcet_admin;
GRANT ALL ON TABLE tag TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE tag TO esgcet_user;
GRANT SELECT ON TABLE tag TO esgcet_reader;


--
-- TOC entry 3219 (class 0 OID 0)
-- Dependencies: 256
-- Name: taxonomy; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE taxonomy FROM PUBLIC;
REVOKE ALL ON TABLE taxonomy FROM esgcet_admin;
GRANT ALL ON TABLE taxonomy TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE taxonomy TO esgcet_user;
GRANT SELECT ON TABLE taxonomy TO esgcet_reader;


--
-- TOC entry 3220 (class 0 OID 0)
-- Dependencies: 257
-- Name: time_coordinate_axis; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE time_coordinate_axis FROM PUBLIC;
REVOKE ALL ON TABLE time_coordinate_axis FROM esgcet_admin;
GRANT ALL ON TABLE time_coordinate_axis TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE time_coordinate_axis TO esgcet_user;
GRANT SELECT ON TABLE time_coordinate_axis TO esgcet_reader;


--
-- TOC entry 3221 (class 0 OID 0)
-- Dependencies: 258
-- Name: time_frequency; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE time_frequency FROM PUBLIC;
REVOKE ALL ON TABLE time_frequency FROM esgcet_admin;
GRANT ALL ON TABLE time_frequency TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE time_frequency TO esgcet_user;
GRANT SELECT ON TABLE time_frequency TO esgcet_reader;


--
-- TOC entry 3222 (class 0 OID 0)
-- Dependencies: 259
-- Name: topic; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE topic FROM PUBLIC;
REVOKE ALL ON TABLE topic FROM esgcet_admin;
GRANT ALL ON TABLE topic TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE topic TO esgcet_user;
GRANT SELECT ON TABLE topic TO esgcet_reader;


--
-- TOC entry 3223 (class 0 OID 0)
-- Dependencies: 260
-- Name: unit; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE unit FROM PUBLIC;
REVOKE ALL ON TABLE unit FROM esgcet_admin;
GRANT ALL ON TABLE unit TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE unit TO esgcet_user;
GRANT SELECT ON TABLE unit TO esgcet_reader;


--
-- TOC entry 3224 (class 0 OID 0)
-- Dependencies: 262
-- Name: variable; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE variable FROM PUBLIC;
REVOKE ALL ON TABLE variable FROM esgcet_admin;
GRANT ALL ON TABLE variable TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE variable TO esgcet_user;
GRANT SELECT ON TABLE variable TO esgcet_reader;


--
-- TOC entry 3225 (class 0 OID 0)
-- Dependencies: 263
-- Name: variable_standard_name_xref; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE variable_standard_name_xref FROM PUBLIC;
REVOKE ALL ON TABLE variable_standard_name_xref FROM esgcet_admin;
GRANT ALL ON TABLE variable_standard_name_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE variable_standard_name_xref TO esgcet_user;
GRANT SELECT ON TABLE variable_standard_name_xref TO esgcet_reader;


--
-- TOC entry 3226 (class 0 OID 0)
-- Dependencies: 264
-- Name: z_coordinate_axis; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE z_coordinate_axis FROM PUBLIC;
REVOKE ALL ON TABLE z_coordinate_axis FROM esgcet_admin;
GRANT ALL ON TABLE z_coordinate_axis TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE z_coordinate_axis TO esgcet_user;
GRANT SELECT ON TABLE z_coordinate_axis TO esgcet_reader;


--
-- TOC entry 3227 (class 0 OID 0)
-- Dependencies: 265
-- Name: z_positive_type; Type: ACL; Schema: metadata; Owner: esgcet_admin
--

REVOKE ALL ON TABLE z_positive_type FROM PUBLIC;
REVOKE ALL ON TABLE z_positive_type FROM esgcet_admin;
GRANT ALL ON TABLE z_positive_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE z_positive_type TO esgcet_user;
GRANT SELECT ON TABLE z_positive_type TO esgcet_reader;


SET search_path = metrics, pg_catalog;

--
-- TOC entry 3228 (class 0 OID 0)
-- Dependencies: 266
-- Name: calendar; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE calendar FROM PUBLIC;
REVOKE ALL ON TABLE calendar FROM esgcet_admin;
GRANT ALL ON TABLE calendar TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE calendar TO esgcet_user;
GRANT SELECT ON TABLE calendar TO esgcet_reader;


--
-- TOC entry 3229 (class 0 OID 0)
-- Dependencies: 267
-- Name: clickstream; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE clickstream FROM PUBLIC;
REVOKE ALL ON TABLE clickstream FROM esgcet_admin;
GRANT ALL ON TABLE clickstream TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE clickstream TO esgcet_user;
GRANT SELECT ON TABLE clickstream TO esgcet_reader;


--
-- TOC entry 3230 (class 0 OID 0)
-- Dependencies: 268
-- Name: datanode_harvest; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE datanode_harvest FROM PUBLIC;
REVOKE ALL ON TABLE datanode_harvest FROM esgcet_admin;
GRANT ALL ON TABLE datanode_harvest TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE datanode_harvest TO esgcet_user;
GRANT SELECT ON TABLE datanode_harvest TO esgcet_reader;


--
-- TOC entry 3231 (class 0 OID 0)
-- Dependencies: 269
-- Name: file_download; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE file_download FROM PUBLIC;
REVOKE ALL ON TABLE file_download FROM esgcet_admin;
GRANT ALL ON TABLE file_download TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE file_download TO esgcet_user;
GRANT SELECT ON TABLE file_download TO esgcet_reader;


--
-- TOC entry 3232 (class 0 OID 0)
-- Dependencies: 270
-- Name: numbers; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE numbers FROM PUBLIC;
REVOKE ALL ON TABLE numbers FROM esgcet_admin;
GRANT ALL ON TABLE numbers TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE numbers TO esgcet_user;
GRANT SELECT ON TABLE numbers TO esgcet_reader;


--
-- TOC entry 3233 (class 0 OID 0)
-- Dependencies: 271
-- Name: operating_system_type; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE operating_system_type FROM PUBLIC;
REVOKE ALL ON TABLE operating_system_type FROM esgcet_admin;
GRANT ALL ON TABLE operating_system_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE operating_system_type TO esgcet_user;
GRANT SELECT ON TABLE operating_system_type TO esgcet_reader;


--
-- TOC entry 3234 (class 0 OID 0)
-- Dependencies: 272
-- Name: user_agent; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE user_agent FROM PUBLIC;
REVOKE ALL ON TABLE user_agent FROM esgcet_admin;
GRANT ALL ON TABLE user_agent TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE user_agent TO esgcet_user;
GRANT SELECT ON TABLE user_agent TO esgcet_reader;


--
-- TOC entry 3235 (class 0 OID 0)
-- Dependencies: 273
-- Name: user_agent_type; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE user_agent_type FROM PUBLIC;
REVOKE ALL ON TABLE user_agent_type FROM esgcet_admin;
GRANT ALL ON TABLE user_agent_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE user_agent_type TO esgcet_user;
GRANT SELECT ON TABLE user_agent_type TO esgcet_reader;


--
-- TOC entry 3236 (class 0 OID 0)
-- Dependencies: 274
-- Name: user_login; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE user_login FROM PUBLIC;
REVOKE ALL ON TABLE user_login FROM esgcet_admin;
GRANT ALL ON TABLE user_login TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE user_login TO esgcet_user;
GRANT SELECT ON TABLE user_login TO esgcet_reader;


--
-- TOC entry 3237 (class 0 OID 0)
-- Dependencies: 275
-- Name: user_login_type; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE user_login_type FROM PUBLIC;
REVOKE ALL ON TABLE user_login_type FROM esgcet_admin;
GRANT ALL ON TABLE user_login_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE user_login_type TO esgcet_user;
GRANT SELECT ON TABLE user_login_type TO esgcet_reader;


--
-- TOC entry 3238 (class 0 OID 0)
-- Dependencies: 276
-- Name: user_search; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE user_search FROM PUBLIC;
REVOKE ALL ON TABLE user_search FROM esgcet_admin;
GRANT ALL ON TABLE user_search TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE user_search TO esgcet_user;
GRANT SELECT ON TABLE user_search TO esgcet_reader;


--
-- TOC entry 3239 (class 0 OID 0)
-- Dependencies: 277
-- Name: user_search_facet; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE user_search_facet FROM PUBLIC;
REVOKE ALL ON TABLE user_search_facet FROM esgcet_admin;
GRANT ALL ON TABLE user_search_facet TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE user_search_facet TO esgcet_user;
GRANT SELECT ON TABLE user_search_facet TO esgcet_reader;


--
-- TOC entry 3240 (class 0 OID 0)
-- Dependencies: 278
-- Name: user_search_type; Type: ACL; Schema: metrics; Owner: esgcet_admin
--

REVOKE ALL ON TABLE user_search_type FROM PUBLIC;
REVOKE ALL ON TABLE user_search_type FROM esgcet_admin;
GRANT ALL ON TABLE user_search_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE user_search_type TO esgcet_user;
GRANT SELECT ON TABLE user_search_type TO esgcet_reader;


SET search_path = public, pg_catalog;

--
-- TOC entry 3243 (class 0 OID 0)
-- Dependencies: 281
-- Name: duplicate_order_count; Type: ACL; Schema: public; Owner: esgcet_admin
--

REVOKE ALL ON TABLE duplicate_order_count FROM PUBLIC;
REVOKE ALL ON TABLE duplicate_order_count FROM esgcet_admin;
GRANT ALL ON TABLE duplicate_order_count TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE duplicate_order_count TO esgcet_user;
GRANT SELECT ON TABLE duplicate_order_count TO esgcet_reader;


SET search_path = security, pg_catalog;

--
-- TOC entry 3245 (class 0 OID 0)
-- Dependencies: 282
-- Name: authorization_token; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE authorization_token FROM PUBLIC;
REVOKE ALL ON TABLE authorization_token FROM esgcet_admin;
GRANT ALL ON TABLE authorization_token TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE authorization_token TO esgcet_user;
GRANT SELECT ON TABLE authorization_token TO esgcet_reader;


--
-- TOC entry 3246 (class 0 OID 0)
-- Dependencies: 283
-- Name: group; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE "group" FROM PUBLIC;
REVOKE ALL ON TABLE "group" FROM esgcet_admin;
GRANT ALL ON TABLE "group" TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE "group" TO esgcet_user;
GRANT SELECT ON TABLE "group" TO esgcet_reader;


--
-- TOC entry 3247 (class 0 OID 0)
-- Dependencies: 284
-- Name: group_data; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE group_data FROM PUBLIC;
REVOKE ALL ON TABLE group_data FROM esgcet_admin;
GRANT ALL ON TABLE group_data TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE group_data TO esgcet_user;
GRANT SELECT ON TABLE group_data TO esgcet_reader;


--
-- TOC entry 3248 (class 0 OID 0)
-- Dependencies: 285
-- Name: group_data_type; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE group_data_type FROM PUBLIC;
REVOKE ALL ON TABLE group_data_type FROM esgcet_admin;
GRANT ALL ON TABLE group_data_type TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE group_data_type TO esgcet_user;
GRANT SELECT ON TABLE group_data_type TO esgcet_reader;


--
-- TOC entry 3249 (class 0 OID 0)
-- Dependencies: 286
-- Name: group_default_role; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE group_default_role FROM PUBLIC;
REVOKE ALL ON TABLE group_default_role FROM esgcet_admin;
GRANT ALL ON TABLE group_default_role TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE group_default_role TO esgcet_user;
GRANT SELECT ON TABLE group_default_role TO esgcet_reader;


--
-- TOC entry 3250 (class 0 OID 0)
-- Dependencies: 287
-- Name: group_group_data_xref; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE group_group_data_xref FROM PUBLIC;
REVOKE ALL ON TABLE group_group_data_xref FROM esgcet_admin;
GRANT ALL ON TABLE group_group_data_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE group_group_data_xref TO esgcet_user;
GRANT SELECT ON TABLE group_group_data_xref TO esgcet_reader;


--
-- TOC entry 3251 (class 0 OID 0)
-- Dependencies: 288
-- Name: membership; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE membership FROM PUBLIC;
REVOKE ALL ON TABLE membership FROM esgcet_admin;
GRANT ALL ON TABLE membership TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE membership TO esgcet_user;
GRANT SELECT ON TABLE membership TO esgcet_reader;


--
-- TOC entry 3252 (class 0 OID 0)
-- Dependencies: 289
-- Name: user; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE "user" FROM PUBLIC;
REVOKE ALL ON TABLE "user" FROM esgcet_admin;
GRANT ALL ON TABLE "user" TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE "user" TO esgcet_user;
GRANT SELECT ON TABLE "user" TO esgcet_reader;


--
-- TOC entry 3253 (class 0 OID 0)
-- Dependencies: 290
-- Name: myproxy_user; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE myproxy_user FROM PUBLIC;
REVOKE ALL ON TABLE myproxy_user FROM esgcet_admin;
GRANT ALL ON TABLE myproxy_user TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE myproxy_user TO esgcet_user;
GRANT SELECT ON TABLE myproxy_user TO esgcet_reader;


--
-- TOC entry 3254 (class 0 OID 0)
-- Dependencies: 291
-- Name: operation; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE operation FROM PUBLIC;
REVOKE ALL ON TABLE operation FROM esgcet_admin;
GRANT ALL ON TABLE operation TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE operation TO esgcet_user;
GRANT SELECT ON TABLE operation TO esgcet_reader;


--
-- TOC entry 3255 (class 0 OID 0)
-- Dependencies: 292
-- Name: permission; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE permission FROM PUBLIC;
REVOKE ALL ON TABLE permission FROM esgcet_admin;
GRANT ALL ON TABLE permission TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE permission TO esgcet_user;
GRANT SELECT ON TABLE permission TO esgcet_reader;


--
-- TOC entry 3256 (class 0 OID 0)
-- Dependencies: 293
-- Name: principal; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE principal FROM PUBLIC;
REVOKE ALL ON TABLE principal FROM esgcet_admin;
GRANT ALL ON TABLE principal TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE principal TO esgcet_user;
GRANT SELECT ON TABLE principal TO esgcet_reader;


--
-- TOC entry 3257 (class 0 OID 0)
-- Dependencies: 294
-- Name: role; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE role FROM PUBLIC;
REVOKE ALL ON TABLE role FROM esgcet_admin;
GRANT ALL ON TABLE role TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE role TO esgcet_user;
GRANT SELECT ON TABLE role TO esgcet_reader;


--
-- TOC entry 3258 (class 0 OID 0)
-- Dependencies: 295
-- Name: role_operation_xref; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE role_operation_xref FROM PUBLIC;
REVOKE ALL ON TABLE role_operation_xref FROM esgcet_admin;
GRANT ALL ON TABLE role_operation_xref TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE role_operation_xref TO esgcet_user;
GRANT SELECT ON TABLE role_operation_xref TO esgcet_reader;


--
-- TOC entry 3259 (class 0 OID 0)
-- Dependencies: 296
-- Name: status; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE status FROM PUBLIC;
REVOKE ALL ON TABLE status FROM esgcet_admin;
GRANT ALL ON TABLE status TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE status TO esgcet_user;
GRANT SELECT ON TABLE status TO esgcet_reader;


--
-- TOC entry 3260 (class 0 OID 0)
-- Dependencies: 297
-- Name: user_attribute; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE user_attribute FROM PUBLIC;
REVOKE ALL ON TABLE user_attribute FROM esgcet_admin;
GRANT ALL ON TABLE user_attribute TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE user_attribute TO esgcet_user;
GRANT SELECT ON TABLE user_attribute TO esgcet_reader;


--
-- TOC entry 3261 (class 0 OID 0)
-- Dependencies: 298
-- Name: user_data; Type: ACL; Schema: security; Owner: esgcet_admin
--

REVOKE ALL ON TABLE user_data FROM PUBLIC;
REVOKE ALL ON TABLE user_data FROM esgcet_admin;
GRANT ALL ON TABLE user_data TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE user_data TO esgcet_user;
GRANT SELECT ON TABLE user_data TO esgcet_reader;


SET search_path = workspace, pg_catalog;

--
-- TOC entry 3262 (class 0 OID 0)
-- Dependencies: 299
-- Name: charge_code; Type: ACL; Schema: workspace; Owner: esgcet_admin
--

REVOKE ALL ON TABLE charge_code FROM PUBLIC;
REVOKE ALL ON TABLE charge_code FROM esgcet_admin;
GRANT ALL ON TABLE charge_code TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE charge_code TO esgcet_user;
GRANT SELECT ON TABLE charge_code TO esgcet_reader;


--
-- TOC entry 3263 (class 0 OID 0)
-- Dependencies: 300
-- Name: data_transfer_item; Type: ACL; Schema: workspace; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_transfer_item FROM PUBLIC;
REVOKE ALL ON TABLE data_transfer_item FROM esgcet_admin;
GRANT ALL ON TABLE data_transfer_item TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_transfer_item TO esgcet_user;
GRANT SELECT ON TABLE data_transfer_item TO esgcet_reader;


--
-- TOC entry 3264 (class 0 OID 0)
-- Dependencies: 301
-- Name: data_transfer_request; Type: ACL; Schema: workspace; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_transfer_request FROM PUBLIC;
REVOKE ALL ON TABLE data_transfer_request FROM esgcet_admin;
GRANT ALL ON TABLE data_transfer_request TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_transfer_request TO esgcet_user;
GRANT SELECT ON TABLE data_transfer_request TO esgcet_reader;


--
-- TOC entry 3265 (class 0 OID 0)
-- Dependencies: 302
-- Name: data_transfer_request_error; Type: ACL; Schema: workspace; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_transfer_request_error FROM PUBLIC;
REVOKE ALL ON TABLE data_transfer_request_error FROM esgcet_admin;
GRANT ALL ON TABLE data_transfer_request_error TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_transfer_request_error TO esgcet_user;
GRANT SELECT ON TABLE data_transfer_request_error TO esgcet_reader;


--
-- TOC entry 3266 (class 0 OID 0)
-- Dependencies: 303
-- Name: data_transfer_request_status; Type: ACL; Schema: workspace; Owner: esgcet_admin
--

REVOKE ALL ON TABLE data_transfer_request_status FROM PUBLIC;
REVOKE ALL ON TABLE data_transfer_request_status FROM esgcet_admin;
GRANT ALL ON TABLE data_transfer_request_status TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE data_transfer_request_status TO esgcet_user;
GRANT SELECT ON TABLE data_transfer_request_status TO esgcet_reader;


--
-- TOC entry 3267 (class 0 OID 0)
-- Dependencies: 304
-- Name: search_criteria; Type: ACL; Schema: workspace; Owner: esgcet_admin
--

REVOKE ALL ON TABLE search_criteria FROM PUBLIC;
REVOKE ALL ON TABLE search_criteria FROM esgcet_admin;
GRANT ALL ON TABLE search_criteria TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE search_criteria TO esgcet_user;
GRANT SELECT ON TABLE search_criteria TO esgcet_reader;


--
-- TOC entry 3268 (class 0 OID 0)
-- Dependencies: 305
-- Name: search_facet; Type: ACL; Schema: workspace; Owner: esgcet_admin
--

REVOKE ALL ON TABLE search_facet FROM PUBLIC;
REVOKE ALL ON TABLE search_facet FROM esgcet_admin;
GRANT ALL ON TABLE search_facet TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE search_facet TO esgcet_user;
GRANT SELECT ON TABLE search_facet TO esgcet_reader;


--
-- TOC entry 3269 (class 0 OID 0)
-- Dependencies: 306
-- Name: search_result; Type: ACL; Schema: workspace; Owner: esgcet_admin
--

REVOKE ALL ON TABLE search_result FROM PUBLIC;
REVOKE ALL ON TABLE search_result FROM esgcet_admin;
GRANT ALL ON TABLE search_result TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE search_result TO esgcet_user;
GRANT SELECT ON TABLE search_result TO esgcet_reader;


--
-- TOC entry 3270 (class 0 OID 0)
-- Dependencies: 307
-- Name: workspace; Type: ACL; Schema: workspace; Owner: esgcet_admin
--

REVOKE ALL ON TABLE workspace FROM PUBLIC;
REVOKE ALL ON TABLE workspace FROM esgcet_admin;
GRANT ALL ON TABLE workspace TO esgcet_admin;
GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE workspace TO esgcet_user;
GRANT SELECT ON TABLE workspace TO esgcet_reader;


-- Completed on 2013-02-11 14:02:48

--
-- PostgreSQL database dump complete
--

