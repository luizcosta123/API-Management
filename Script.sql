PGDMP                         {            managementDB    15.3    15.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            	           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            
           1262    24825    managementDB    DATABASE     �   CREATE DATABASE "managementDB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE "managementDB";
                luiz    false            �            1259    24827    pessoa    TABLE     I  CREATE TABLE public.pessoa (
    id bigint NOT NULL,
    departamento character varying(255),
    nome character varying(255),
    CONSTRAINT pessoa_departamento_check CHECK (((departamento)::text = ANY ((ARRAY['FINANCEIRO'::character varying, 'COMERCIAL'::character varying, 'DESENVOLVIMENTO'::character varying])::text[])))
);
    DROP TABLE public.pessoa;
       public         heap    luiz    false            �            1259    24826    pessoa_id_seq    SEQUENCE     v   CREATE SEQUENCE public.pessoa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.pessoa_id_seq;
       public          luiz    false    215                       0    0    pessoa_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.pessoa_id_seq OWNED BY public.pessoa.id;
          public          luiz    false    214            �            1259    24837    tarefa    TABLE     �  CREATE TABLE public.tarefa (
    id bigint NOT NULL,
    departamento character varying(255),
    descricao character varying(255),
    duracao integer,
    finalizado boolean,
    prazo date,
    titulo character varying(255),
    id_pessoa bigint,
    CONSTRAINT tarefa_departamento_check CHECK (((departamento)::text = ANY ((ARRAY['FINANCEIRO'::character varying, 'COMERCIAL'::character varying, 'DESENVOLVIMENTO'::character varying])::text[])))
);
    DROP TABLE public.tarefa;
       public         heap    luiz    false            �            1259    24836    tarefa_id_seq    SEQUENCE     v   CREATE SEQUENCE public.tarefa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.tarefa_id_seq;
       public          luiz    false    217                       0    0    tarefa_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.tarefa_id_seq OWNED BY public.tarefa.id;
          public          luiz    false    216            j           2604    24830 	   pessoa id    DEFAULT     f   ALTER TABLE ONLY public.pessoa ALTER COLUMN id SET DEFAULT nextval('public.pessoa_id_seq'::regclass);
 8   ALTER TABLE public.pessoa ALTER COLUMN id DROP DEFAULT;
       public          luiz    false    214    215    215            k           2604    24840 	   tarefa id    DEFAULT     f   ALTER TABLE ONLY public.tarefa ALTER COLUMN id SET DEFAULT nextval('public.tarefa_id_seq'::regclass);
 8   ALTER TABLE public.tarefa ALTER COLUMN id DROP DEFAULT;
       public          luiz    false    216    217    217                      0    24827    pessoa 
   TABLE DATA           8   COPY public.pessoa (id, departamento, nome) FROM stdin;
    public          luiz    false    215   �                 0    24837    tarefa 
   TABLE DATA           l   COPY public.tarefa (id, departamento, descricao, duracao, finalizado, prazo, titulo, id_pessoa) FROM stdin;
    public          luiz    false    217   n                  0    0    pessoa_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.pessoa_id_seq', 6, true);
          public          luiz    false    214                       0    0    tarefa_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.tarefa_id_seq', 6, true);
          public          luiz    false    216            o           2606    24835    pessoa pessoa_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.pessoa DROP CONSTRAINT pessoa_pkey;
       public            luiz    false    215            q           2606    24845    tarefa tarefa_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.tarefa
    ADD CONSTRAINT tarefa_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.tarefa DROP CONSTRAINT tarefa_pkey;
       public            luiz    false    217            r           2606    24846 "   tarefa fk7bl3c6v85s4wnaeu86ht8d76k    FK CONSTRAINT     �   ALTER TABLE ONLY public.tarefa
    ADD CONSTRAINT fk7bl3c6v85s4wnaeu86ht8d76k FOREIGN KEY (id_pessoa) REFERENCES public.pessoa(id);
 L   ALTER TABLE ONLY public.tarefa DROP CONSTRAINT fk7bl3c6v85s4wnaeu86ht8d76k;
       public          luiz    false    217    215    3183               g   x�3�tqv���	��u����I-��2���?�8�˘����5���ч�9�('��˄��������3ȟ�7�$#����C�kJ^"������L�`� L&         6  x�u�Mj�0�ףS�)�IB���Kb�[��fb+��֤c�@��B����*�8�_A $��޼���c�n��&Y��Skd�#n��cM�T��J��/'��$��j�/��V�8�d,Zr{#-��J�X�v�ՖZ�S��jD�$]�Q��l�֥+3dq�j�έ;˦�l墢�	��2]�4J3�TL!��q%��3��m���2Vɝ.l�U��{d��gպ�?���]3���m$ �bv92b֕f��*i�/>�T�,.z��ٝ�+a&�G閪x��qh�.��sɟ�x?�8���`s�|#���ɿ�     