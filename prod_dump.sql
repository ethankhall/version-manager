--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: repository_commit; Type: TABLE; Schema: public; Owner: version_manager; Tablespace: 
--

CREATE TABLE repository_commit (
    id bigint NOT NULL,
    commit_id character varying(40) NOT NULL,
    version character varying(120) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    vcs_repo bigint NOT NULL,
    parent_commit bigint
);


ALTER TABLE public.repository_commit OWNER TO version_manager;

--
-- Name: repository_commit_id_seq; Type: SEQUENCE; Schema: public; Owner: version_manager
--

CREATE SEQUENCE repository_commit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repository_commit_id_seq OWNER TO version_manager;

--
-- Name: repository_commit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: version_manager
--

ALTER SEQUENCE repository_commit_id_seq OWNED BY repository_commit.id;


--
-- Name: schema_version; Type: TABLE; Schema: public; Owner: version_manager; Tablespace:
--

CREATE TABLE schema_version (
    version_rank integer NOT NULL,
    installed_rank integer NOT NULL,
    version character varying(50) NOT NULL,
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.schema_version OWNER TO version_manager;

--
-- Name: vcs_repo_data; Type: TABLE; Schema: public; Owner: version_manager; Tablespace:
--

CREATE TABLE vcs_repo_data (
    id bigint NOT NULL,
    uuid uuid NOT NULL,
    repo_name text NOT NULL,
    repo_token character varying(60) NOT NULL,
    url text,
    version_bumper bigint NOT NULL
);


ALTER TABLE public.vcs_repo_data OWNER TO version_manager;

--
-- Name: vcs_repo_data_id_seq; Type: SEQUENCE; Schema: public; Owner: version_manager
--

CREATE SEQUENCE vcs_repo_data_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vcs_repo_data_id_seq OWNER TO version_manager;

--
-- Name: vcs_repo_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: version_manager
--

ALTER SEQUENCE vcs_repo_data_id_seq OWNED BY vcs_repo_data.id;


--
-- Name: version_bumper; Type: TABLE; Schema: public; Owner: version_manager; Tablespace:
--

CREATE TABLE version_bumper (
    id bigint NOT NULL,
    bumper_name text NOT NULL,
    class_name text NOT NULL,
    description text NOT NULL
);


ALTER TABLE public.version_bumper OWNER TO version_manager;

--
-- Name: version_bumper_id_seq; Type: SEQUENCE; Schema: public; Owner: version_manager
--

CREATE SEQUENCE version_bumper_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.version_bumper_id_seq OWNER TO version_manager;

--
-- Name: version_bumper_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: version_manager
--

ALTER SEQUENCE version_bumper_id_seq OWNED BY version_bumper.id;


--
-- Name: uniqueId; Type: DEFAULT; Schema: public; Owner: version_manager
--

ALTER TABLE ONLY repository_commit ALTER COLUMN id SET DEFAULT nextval('repository_commit_id_seq'::regclass);


--
-- Name: uniqueId; Type: DEFAULT; Schema: public; Owner: version_manager
--

ALTER TABLE ONLY vcs_repo_data ALTER COLUMN id SET DEFAULT nextval('vcs_repo_data_id_seq'::regclass);


--
-- Name: uniqueId; Type: DEFAULT; Schema: public; Owner: version_manager
--

ALTER TABLE ONLY version_bumper ALTER COLUMN id SET DEFAULT nextval('version_bumper_id_seq'::regclass);


--
-- Data for Name: repository_commit; Type: TABLE DATA; Schema: public; Owner: version_manager
--

COPY repository_commit (id, commit_id, version, created_at, vcs_repo, parent_commit) FROM stdin;
1	efee361b8f87056147ca290bc958881ff082505a	0.0.10	2015-11-01 18:57:33.493+00	3	\N
2	d5238d3d81bf7e3b89e6c463126f7d4dbc72bb19	0.0.11	2015-11-01 18:57:33.545+00	3	1
3	76b70f12f21dcc86b8f8c8101434a3b29a3de7fa	0.0.12	2015-11-01 18:57:33.56+00	3	2
4	0b15c810eba0b219b3f169b9fc62889c03bad8e3	0.0.13	2015-11-01 18:57:33.566+00	3	3
5	a247ee87f224c2c5246fa4b308c4fcadba2a3068	0.0.14	2015-11-01 18:57:33.572+00	3	4
6	5923a88296fb63950549b4140044171dc0303f6a	0.0.15	2015-11-01 18:57:33.577+00	3	5
7	daecb3bfc10a383bac762fda4aac0bf551914b8c	0.0.16	2015-11-01 18:57:33.591+00	3	6
8	303e05574debeac09a1e40947c91a0b4b13f61b0	0.0.3	2015-11-01 18:57:33.597+00	3	7
9	5e5ad036a03d5307471a9b32f3747d6419dad808	0.0.4	2015-11-01 18:57:33.605+00	3	8
10	5620ed7ec9e2af82a83dfe1c0f0c8c4f84f42a22	0.0.5	2015-11-01 18:57:33.61+00	3	9
11	ad87e9c359e097c34a77d138153387f065ef2cec	0.0.6	2015-11-01 18:57:33.613+00	3	10
12	ea95fab25f99e247e2ee145e9a9e76f4f4ce4f64	0.0.7	2015-11-01 18:57:33.626+00	3	11
13	9ec41f48d6865cd369c1764485c10aacc32ae101	0.0.9	2015-11-01 18:57:33.629+00	3	12
14	54e69afe1223d6bf75b25213f25b0e1f8cd12903	1.0.0	2015-11-01 18:57:33.633+00	3	13
15	b712fb7ae4e4c9d260bfd4b168fd0c6358c04a85	1.0.1	2015-11-01 18:57:33.637+00	3	14
16	2d936a40da9af3a2ced9485979db607f27e508ea	1.0.2	2015-11-01 18:57:33.642+00	3	15
17	8f0f857954db1542ead4971f64e14343715a47e4	1.0.10	2015-11-01 18:57:33.655+00	3	16
18	b179011319b7b5cdaca722dd5de87736f6dcf251	1.0.11	2015-11-01 18:57:33.661+00	3	17
19	c6bd979fd3e1f14b4c275b2603e566bbf294bc10	1.0.13	2015-11-01 18:57:33.665+00	3	18
20	828fab72d3f3baf615c9b971bd0193d300cc888e	1.0.5	2015-11-01 18:57:33.669+00	3	19
21	51199ee65da3216f21037fff2f08eead20e299e8	1.0.6	2015-11-01 18:57:33.678+00	3	20
22	bbc0e6c05b9a6ee685a59290d5c06b688fd71044	1.0.7	2015-11-01 18:57:33.684+00	3	21
23	99c844805d43711d22870fd330f3486cf1a8ab1c	1.0.8	2015-11-01 18:57:33.686+00	3	22
24	0222abd53c5f96632ab28a1b174a7ec898127e89	1.0.9	2015-11-01 18:57:33.691+00	3	23
25	5ab755bc3268760b795a95254993ce2fab4e6cd5	2.0.0	2015-11-01 18:57:33.695+00	3	24
26	ee4a5f124039140c177996b554a2f74387ef7c73	2.0.2	2015-11-01 18:57:33.699+00	3	25
27	1a510b098c0c7adb1fed5eeeb8cd5803c8d75f59	0.0.1	2015-11-03 04:33:52.122+00	4	\N
28	54349f347eb06df2de5d2131cb4bee3faf164714	1.0.0	2015-11-03 04:36:41.986+00	4	27
29	c68fd7093643308d2fff3dc23fe1ebb849c30245	1.0.1	2015-11-08 21:12:53.326+00	4	28
30	ed9d909764cde75aa08b7912c9e6173b76b29269	1.0.2	2015-11-27 03:14:48.038+00	4	29
31	3d1f1fcb5d46285ccae8a1124c451aa350327087	0.0.1	2015-11-29 21:08:06.858+00	5	\N
32	a30bdcf03c2a3e597a7fb5f7a2b2e7ed87894c5f	0.0.2	2015-11-29 21:19:02.365+00	5	31
33	04c0536c08ffaa3202d511f10a516833f375ee83	0.0.3	2015-11-29 21:19:27.327+00	5	32
34	daf544b2bf1da543c8277d09705884c5bb946132	0.5.0	2015-11-29 21:34:06.209+00	5	33
35	83de9a788ed705f6632bc7e4d8dec326d8a5268e	0.5.1	2015-11-29 22:49:51.531+00	5	34
36	f80c04f5e284cacaba55f177daea109af68fabbb	0.5.2	2015-11-29 22:54:25.849+00	5	35
37	f92a9229f96578f945a2e847ab080481869aa7c5	0.5.3	2015-11-29 23:14:38.662+00	5	36
38	9f8b86e172e26d86c70ea7a5da36639ea144fe4f	1.0.3	2015-11-29 23:30:30.873+00	4	30
39	93d0be4982712afb800548827a4f1c719f3c9c3a	0.5.4	2015-12-05 20:20:11.261+00	5	37
40	5ab3f54d50a07ae4a8666284270b33b440ea5845	0.5.5	2015-12-05 20:21:46.638+00	5	39
41	425fe24553b14394ac67206c976e279e5698344a	0.5.6	2015-12-06 08:36:08.936+00	5	40
42	d4b00837e89e84bd7b6e1609b54cb38d1d750166	0.5.7	2015-12-06 22:00:17.903+00	5	41
43	3992ea90e539ac640169c43f3be7a09f5f551dea	0.5.8	2015-12-06 23:26:32.286+00	5	42
44	882c9ec767e1add0cf47538c4fdb2150db7e9478	0.5.9	2015-12-07 00:50:07.428+00	5	43
45	e60a344ab66bb390f57b10c94543bd1696438195	0.5.10	2015-12-08 05:26:55.477+00	5	44
46	d8c01a2ac0da4d0d7bdc6253974b94d81936fe90	0.5.11	2015-12-08 06:10:00.382+00	5	45
47	f9a7bc779bd4e6d8ab1637ce57f0de7ebbad7a43	0.5.12	2015-12-08 06:21:14.903+00	5	46
48	99aa3fc97b81c9e3cbbc7ef40221328a831ac3a4	0.5.13	2015-12-12 05:20:59.804+00	5	47
49	d37c92a15f54c8c895fe294291ca55c45da96f9a	0.0.1	2015-12-12 22:10:49.815+00	6	\N
50	5e370d6499ab79add986aa1bca6ba4682fa9b2f1	0.5.14	2015-12-12 23:35:32.505+00	5	48
51	0a5dd50886ddbf919f062ae687f73909da104942	0.5.15	2015-12-13 17:18:25.503+00	5	50
52	d732da5602cc5b1b8f17cb5de663d4cccb21eeb3	0.5.16	2015-12-13 18:32:59.191+00	5	51
53	82ffdceb5f75f6af635b08895763e92ee492caf1	0.0.2	2015-12-13 18:41:51.471+00	6	49
54	dcdb86d2a8ef387c6da9b25a5f127886e050f271	0.0.3	2015-12-13 19:03:46.809+00	6	53
55	2a73238279de0b851566824bec7bff2bd524936e	0.0.4	2015-12-13 19:39:22.01+00	6	54
57	4e0d010aa615d7c795f009f3871c9b61858970fa	1.0.0	2015-12-13 19:39:54.782+00	6	55
58	66a90473fb009a51f754f358fd4837e8e63da847	0.5.17	2015-12-13 21:09:14.625+00	5	52
59	ee11c5e09c84c950556bd9e853973f39ec470654	0.5.18	2015-12-14 01:56:48.793+00	5	58
60	7b644b81e88740905f42deca8a469f501c8273db	0.5.19	2015-12-14 03:03:52.232+00	5	59
61	8a0339e1da6d65332e91f040d08c7c8f874fc43e	0.5.20	2015-12-14 05:10:05.657+00	5	60
62	edf084983e1925de73ed60f01f9d8191b996269f	1.0.4	2015-12-14 06:29:41.405+00	4	38
63	4be1a916857beca84c86e4f5b5a5ce97d4cf7ad8	0.5.21	2015-12-15 04:30:29.386+00	5	61
64	1c9b4048ac0ee1114978587e26ffdf8ce156d795	0.5.22	2015-12-15 04:32:08.513+00	5	63
65	d5ec0d243160171abe61e5f2d33f15aedaf91b0a	0.5.23	2015-12-15 04:33:31.619+00	5	64
66	e79f946f337738575064b43b1dd9f00b5a8699e0	0.5.24	2015-12-15 04:40:24.165+00	5	65
67	1dd20e4a2d20db9bc4b50d41899d4fa266a5cf77	0.5.25	2015-12-15 04:41:25.937+00	5	66
68	e1d4653a46086ee2a0042a96fb73f18eb439ba6f	0.5.26	2015-12-15 04:44:22.771+00	5	67
69	b221f55f777a858c4db67f8898767e04cef58536	0.5.27	2015-12-15 05:13:39.475+00	5	68
70	f0d3cc657c20faf1ea4aedb35e52022ab986562f	0.5.28	2015-12-15 05:25:39.575+00	5	69
71	6b2b38dede7812a5d059e5f8db6ab7fe8684d147	0.5.29	2015-12-15 05:34:09.976+00	5	70
72	b651b5b54e710bfb2dec236f375b5234ba6db60f	0.5.30	2015-12-15 06:03:14.577+00	5	71
73	3fb1c4e72f136ce3881f8edf41d9e87d85feb942	0.5.31	2015-12-15 06:18:11.318+00	5	72
74	c63f2fcc1db2775aa2790c65b947b95805b5337c	0.5.32	2015-12-15 06:23:09.308+00	5	73
75	adf6b5c79bc73f865366da67125897215587999f	0.5.33	2015-12-15 06:38:33.843+00	5	74
76	b3a6a5ccaba7cf3d6119c2f07458e7d186836706	0.5.34	2015-12-16 03:24:07.426+00	5	75
77	da9383f90527808a15d9424b13f4d5df89efbd83	0.5.35	2015-12-18 01:12:37.669+00	5	76
78	4a005e89773a1842d6395722f42fea0cae9216d9	0.5.36	2015-12-18 06:44:07.289+00	5	77
79	0041f178d860955fcd4fd8bd280fe2ddb5c2b426	0.5.37	2015-12-18 15:00:55.928+00	5	78
80	a36eefa14b57c88bc903cce54a255f5f7c97999c	0.5.38	2015-12-18 15:03:02.725+00	5	79
81	2956cb8c3d9d4660be0c528b296b4dcba29e6fed	1.0.1	2015-12-19 01:45:52.531+00	6	57
82	35a750377acc7d45e389685c91a48c8e7e110a19	0.5.39	2015-12-19 02:24:54.754+00	5	80
83	d18e8724d0c628607ae00e1a42e81aa689477666	0.5.40	2015-12-19 02:37:03.713+00	5	82
84	a7b65e582584a9a5297f5af1479e8495e9d2d34e	0.5.41	2015-12-19 02:42:04.982+00	5	83
85	af33e46848a9a0abc1648175a0a286a664113597	0.5.42	2015-12-19 02:46:04.034+00	5	84
86	7894ced73692fb144c8667c7a940508f10eb45fc	0.5.43	2015-12-19 02:50:57.747+00	5	85
87	0a61580f814ef8da5adf4dd0728e9e8d1ad792c6	0.5.44	2015-12-19 03:15:02.48+00	5	86
88	7f5e22d95591a385f07c8d96ea701426e52b4203	0.5.45	2015-12-19 03:57:43.625+00	5	87
89	c9aa9d1de08967d5d2a3459040445b7b74b454e4	0.5.46	2015-12-19 04:13:41.069+00	5	88
90	c43e3958a1ee51045fe57bdb1884656dc18531a0	0.5.47	2015-12-19 04:14:35.72+00	5	89
91	d2bcc2ba7fef352daa68947ff6196cf2210531b5	0.5.48	2015-12-19 04:18:24.256+00	5	90
92	dd5f3bbe13b3e2dfd98c899b4cbd5f7e14fae808	0.5.49	2015-12-19 04:24:09.083+00	5	91
93	e6e8cbf17d0d686447ca16a3c7fbaa26038220b5	0.5.50	2015-12-19 04:36:11.206+00	5	92
94	bd8c47f904117129ea12f55eb2c715156ca63ef4	0.5.51	2015-12-19 04:44:03.439+00	5	93
95	6f4105bce9c52d2f305a65ce5690e0651e8f7f8f	0.5.52	2015-12-19 04:47:07.789+00	5	94
96	e3d3a5bad21925cd4ed56388f7fb05ea19e77cf7	0.5.53	2015-12-19 05:08:14.6+00	5	95
97	cd6b304f8b46e64b501db6698cdc1dfd1d32d2b7	0.5.54	2015-12-19 05:16:23.695+00	5	96
98	55bf19d61a9a27a8b7fd07c97fc9e7e47739694c	0.5.55	2015-12-19 05:21:32.216+00	5	97
99	e86bb4b93766a179f7baae8ad191c017f502c129	0.5.56	2015-12-19 05:36:58.335+00	5	98
100	d5ea74a8d01a51ad75a498717c772392ad48acbf	1.0.2	2015-12-19 17:25:59.373+00	6	81
101	4238f0eb0297cdf04844c37521316d492170fd48	0.5.57	2015-12-19 18:35:15.551+00	5	99
102	3867cbff4cfd487c8a8d6d111e61b9e639bd9214	0.5.58	2015-12-19 18:42:34.761+00	5	101
103	fa2ec03e6a5f7f55ffe722673b1eb0866f93abf2	0.5.59	2015-12-19 18:45:07.248+00	5	102
104	653e5ef3058f3faab076fad904642c307d0b157e	0.5.60	2015-12-19 18:48:49.147+00	5	103
105	60f985c14079252a2a63a148f80ab7fdd1e97d95	0.5.61	2015-12-19 18:56:01.637+00	5	104
106	bb67d62772b76b95bcad5a94dd86d4c7cffe90d0	0.5.62	2015-12-19 19:00:21.973+00	5	105
107	a1c50c94601aa15345af077ffbca0cfe876f54ca	0.5.63	2015-12-19 19:15:22.432+00	5	106
108	7c8147347e9a38ef34ffa4f830b38323376e5b01	0.5.64	2015-12-19 19:50:22.923+00	5	107
109	d0344ccfcfd8e816bf77b34ad4e4cfc792f72d40	0.5.65	2015-12-19 19:55:17.281+00	5	108
110	44551b7b373869cd687d0478c9dffe5ab091fe2f	0.5.66	2015-12-19 19:59:28.707+00	5	109
111	3fd397d232633304f3b8fe9a794af5e94039f9e8	0.5.67	2015-12-19 20:32:56.795+00	5	110
112	223ffdb132d6077eeba0e60cf4ae82b76dfcb531	0.5.68	2015-12-19 20:35:44.23+00	5	111
113	6bfbf28c04022c28726cd993bba3dcd57abb3086	0.5.69	2015-12-19 20:42:16.232+00	5	112
114	7a2f1c778b7ad91c23e66eb0bdde1cb95cdf4cc4	0.5.70	2015-12-19 20:45:45.668+00	5	113
115	1e962ba4e47ddfff68c2ef886380d64bf4e56d2e	0.5.71	2015-12-19 20:47:59.516+00	5	114
116	dff9ac0ce148c4515045ece3ed52d23c769eb7fa	0.5.72	2015-12-19 21:11:08.192+00	5	115
117	588bd4e02563b782e4591f663bf3fb8177977758	1.0.5	2015-12-19 22:17:33.009+00	4	62
118	72bf264c3fd951f1851667b1fbc1607419af6ee7	1.0.6	2015-12-19 22:30:51.724+00	4	117
119	672215402668462a8eea7714b2ac028dbe274386	1.0.7	2015-12-19 23:19:38.81+00	4	118
120	a6c0d3464eec7f790d74d57643d2a7a6a4876083	1.0.8	2015-12-19 23:47:56.172+00	4	119
122	5b0609828f15743862977e4ab8527b87bb94e237	1.0.9	2015-12-19 23:52:02.786+00	4	120
123	fa91cece4bb67fa367283da1a94dea887d446435	1.0.10	2015-12-20 01:04:35.527+00	4	122
\.


--
-- Name: repository_commit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: version_manager
--

SELECT pg_catalog.setval('repository_commit_id_seq', 123, true);


--
-- Data for Name: schema_version; Type: TABLE DATA; Schema: public; Owner: version_manager
--

COPY schema_version (version_rank, installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	0.0.1	initial load	SQL	V0.0.1__initial_load.sql	1742320186	version_manager	2015-10-31 23:51:18.952855	45	t
2	2	0.0.2	change vcs constraints	SQL	V0.0.2__change_vcs_constraints.sql	191208261	version_manager	2015-11-01 13:55:53.419487	132	t
3	3	0.0.3	add vsc index	SQL	V0.0.3__add_vsc_index.sql	-220673554	version_manager	2015-11-29 15:56:38.487124	64	t
4	4	0.0.4	vcs repo data index	SQL	V0.0.4__vcs_repo_data_index.sql	773936115	version_manager	2015-11-29 15:56:38.578618	27	t
\.


--
-- Data for Name: vcs_repo_data; Type: TABLE DATA; Schema: public; Owner: version_manager
--

COPY vcs_repo_data (id, uuid, repo_name, repo_token, url, version_bumper) FROM stdin;
3	8c4eefbd-eed3-4891-aed8-ef6f7981c4dd	pity/pity	kFjz4iZgqAzru6NwYyuFKSOZECMzzVDiU7hpcANLhPOcWF2LCjQ6alGJbp3E	git@github.com:pity/pity.git	1
4	3e79966b-a630-4efa-901b-8819c929c5e3	ethankhall/version-manager	08A7jgz3PYAcBFh9yZNY8PmsrnNxpEyHU4mgeTYFsntHDno6yRPMkvk7RuOR	git@github.com:ethankhall/version-manager.git	1
5	e951f054-c924-4887-b8df-b9cad5d06378	ethankhall/chef-repo	IibKkrfYhjZBujNyVIwt80qyftuyYaUL2wPvFHUJ6ldj8gjOrliH40vlMbfA	git@github.com:ethankhall/chef-repo.git	1
6	84452df5-3cdc-4701-a5fd-b4d8e6f986d2	ethankhall/yum-repo	smEfrQrcIWPBNuHlGPzmGQbzfCg2ITwv4kB8CNf8YE0ORLUw1bAzbh282qa2	git@github.com:ethankhall/yum-repo	1
7	a5023889-8758-4aea-a740-eac6800d4331	ethankhall/packer-builder	Zpn8tMpuJLXpdnIXttAlExMR4KPFOa5g3n3xzxrpS4kcXm0RcSpp9Mhn36B0	git@github.com:ethankhall/packer-builder.git	1
\.


--
-- Name: vcs_repo_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: version_manager
--

SELECT pg_catalog.setval('vcs_repo_data_id_seq', 7, true);


--
-- Data for Name: version_bumper; Type: TABLE DATA; Schema: public; Owner: version_manager
--

COPY version_bumper (id, bumper_name, class_name, description) FROM stdin;
1	semver	io.ehdev.conrad.backend.version.bumper.SemanticVersionBumper	SemVer bumper
\.


--
-- Name: version_bumper_id_seq; Type: SEQUENCE SET; Schema: public; Owner: version_manager
--

SELECT pg_catalog.setval('version_bumper_id_seq', 1, true);


--
-- Name: repository_commit_commit_id_vcs_repo_key; Type: CONSTRAINT; Schema: public; Owner: version_manager; Tablespace: 
--

ALTER TABLE ONLY repository_commit
    ADD CONSTRAINT repository_commit_commit_id_vcs_repo_key UNIQUE (commit_id, vcs_repo);


--
-- Name: repository_commit_pkey; Type: CONSTRAINT; Schema: public; Owner: version_manager; Tablespace: 
--

ALTER TABLE ONLY repository_commit
    ADD CONSTRAINT repository_commit_pkey PRIMARY KEY (id);


--
-- Name: schema_version_pk; Type: CONSTRAINT; Schema: public; Owner: version_manager; Tablespace: 
--

ALTER TABLE ONLY schema_version
    ADD CONSTRAINT schema_version_pk PRIMARY KEY (version);


--
-- Name: vcs_repo_data_pkey; Type: CONSTRAINT; Schema: public; Owner: version_manager; Tablespace: 
--

ALTER TABLE ONLY vcs_repo_data
    ADD CONSTRAINT vcs_repo_data_pkey PRIMARY KEY (id);


--
-- Name: vcs_repo_data_repo_name_url_key; Type: CONSTRAINT; Schema: public; Owner: version_manager; Tablespace: 
--

ALTER TABLE ONLY vcs_repo_data
    ADD CONSTRAINT vcs_repo_data_repo_name_url_key UNIQUE (repo_name, url);


--
-- Name: vcs_repo_data_uuid_key; Type: CONSTRAINT; Schema: public; Owner: version_manager; Tablespace: 
--

ALTER TABLE ONLY vcs_repo_data
    ADD CONSTRAINT vcs_repo_data_uuid_key UNIQUE (uuid);


--
-- Name: version_bumper_bumper_name_key; Type: CONSTRAINT; Schema: public; Owner: version_manager; Tablespace: 
--

ALTER TABLE ONLY version_bumper
    ADD CONSTRAINT version_bumper_bumper_name_key UNIQUE (bumper_name);


--
-- Name: version_bumper_class_name_key; Type: CONSTRAINT; Schema: public; Owner: version_manager; Tablespace: 
--

ALTER TABLE ONLY version_bumper
    ADD CONSTRAINT version_bumper_class_name_key UNIQUE (class_name);


--
-- Name: version_bumper_pkey; Type: CONSTRAINT; Schema: public; Owner: version_manager; Tablespace: 
--

ALTER TABLE ONLY version_bumper
    ADD CONSTRAINT version_bumper_pkey PRIMARY KEY (id);


--
-- Name: repository_commit_vcs_repo_idx; Type: INDEX; Schema: public; Owner: version_manager; Tablespace: 
--

CREATE INDEX repository_commit_vcs_repo_idx ON repository_commit USING btree (vcs_repo);


--
-- Name: schema_version_ir_idx; Type: INDEX; Schema: public; Owner: version_manager; Tablespace: 
--

CREATE INDEX schema_version_ir_idx ON schema_version USING btree (installed_rank);


--
-- Name: schema_version_s_idx; Type: INDEX; Schema: public; Owner: version_manager; Tablespace: 
--

CREATE INDEX schema_version_s_idx ON schema_version USING btree (success);


--
-- Name: schema_version_vr_idx; Type: INDEX; Schema: public; Owner: version_manager; Tablespace: 
--

CREATE INDEX schema_version_vr_idx ON schema_version USING btree (version_rank);


--
-- Name: repository_commit_parent_commit_fkey; Type: FK CONSTRAINT; Schema: public; Owner: version_manager
--

ALTER TABLE ONLY repository_commit
    ADD CONSTRAINT repository_commit_parent_commit_fkey FOREIGN KEY (parent_commit) REFERENCES repository_commit(id);


--
-- Name: repository_commit_vcs_repo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: version_manager
--

ALTER TABLE ONLY repository_commit
    ADD CONSTRAINT repository_commit_vcs_repo_fkey FOREIGN KEY (vcs_repo) REFERENCES vcs_repo_data(id);


--
-- Name: vcs_repo_data_version_bumper_fkey; Type: FK CONSTRAINT; Schema: public; Owner: version_manager
--

ALTER TABLE ONLY vcs_repo_data
    ADD CONSTRAINT vcs_repo_data_version_bumper_fkey FOREIGN KEY (version_bumper) REFERENCES version_bumper(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

