--STATIC DATA



--Age range
CREATE TABLE IF NOT EXISTS age_rng(
agrg_id INT NOT NULL PRIMARY KEY,
age_low INT NOT NULL,
age_high INT NOT NULL
);


--Nutritional status
CREATE TABLE IF NOT EXISTS nut_sts(
nsts_id INT NOT NULL PRIMARY KEY,
nsts VARCHAR(255) NOT NULL,
CONSTRAINT uq_nut_sts_nsts UNIQUE (nsts)
);


--Body mass index - nutritional status
CREATE TABLE IF NOT EXISTS bmi_ns(
bmins_id INT NOT NULL PRIMARY KEY,
bmi_low DOUBLE NOT NULL,
bmi_high DOUBLE NOT NULL,
nsts_id INT NOT NULL,
agrg_id INT NOT NULL,
CONSTRAINT fk_bmi_ns_nut_sts FOREIGN KEY (nsts_id) REFERENCES nut_sts(nsts_id),
CONSTRAINT fk_bmi_ns_age_rng FOREIGN KEY (agrg_id) REFERENCES age_rng(agrg_id)
);


--Degree of malnutrition
CREATE TABLE IF NOT EXISTS deg_mal(
degm_id INT NOT NULL PRIMARY KEY,
degm VARCHAR(255) NOT NULL,
CONSTRAINT uq_deg_mal_degm UNIQUE (degm)
);


--Sex
CREATE TABLE IF NOT EXISTS sex(
sex_id INT NOT NULL PRIMARY KEY,
sex VARCHAR(255) NOT NULL,
CONSTRAINT uq_sex_sex UNIQUE (sex)
);


--Shoulder muscle volume - degree of malnutrition
CREATE TABLE IF NOT EXISTS smv_dm(
smv_id INT NOT NULL PRIMARY KEY,
smv_low DOUBLE NOT NULL,
smv_high DOUBLE NOT NULL,
degm_id INT NOT NULL,
sex_id INT NOT NULL,
CONSTRAINT fk_smv_dm_deg_mal FOREIGN KEY (degm_id) REFERENCES deg_mal(degm_id),
CONSTRAINT fk_smv_dm_sex FOREIGN KEY (sex_id) REFERENCES sex(sex_id)
);


--Skinfold thickness - degree of malnutrition
CREATE TABLE IF NOT EXISTS sft_dm(
sft_id INT NOT NULL PRIMARY KEY,
sft_low DOUBLE NOT NULL,
sft_high DOUBLE NOT NULL,
degm_id INT NOT NULL,
agrg_id INT NOT NULL,
sex_id INT NOT NULL,
CONSTRAINT fk_sft_dm_age_rng FOREIGN KEY (agrg_id) REFERENCES age_rng(agrg_id),
CONSTRAINT fk_sft_dm_deg_mal FOREIGN KEY (degm_id) REFERENCES deg_mal(degm_id),
CONSTRAINT fk_sft_dm_sex FOREIGN KEY (sex_id) REFERENCES sex(sex_id)
);


--Indicator
CREATE TABLE IF NOT EXISTS indicator(
ind_id INT NOT NULL PRIMARY KEY,
ind VARCHAR(255) NOT NULL,
CONSTRAINT uq_indicator_ind UNIQUE (ind)
);


--Clinical and laboratory criteria - degree of malnutrition
CREATE TABLE IF NOT EXISTS lab_dm(
lab_id INT NOT NULL PRIMARY KEY,
res_low DOUBLE NOT NULL,
res_high DOUBLE NOT NULL,
degm_id INT NOT NULL,
ind_id INT NOT NULL,
CONSTRAINT fk_lab_dm_deg_mal FOREIGN KEY (degm_id) REFERENCES deg_mal(degm_id),
CONSTRAINT fk_lab_dm_indicator FOREIGN KEY (ind_id) REFERENCES indicator(ind_id)
);


--Body mass index - degree of malnutrition
CREATE TABLE IF NOT EXISTS bmi_dm(
bmidm_id INT NOT NULL PRIMARY KEY,
bmi_low DOUBLE NOT NULL,
bmi_high DOUBLE NOT NULL,
degm_id INT NOT NULL,
agrg_id INT NOT NULL,
CONSTRAINT fk_bmi_dm_deg_mal FOREIGN KEY (degm_id) REFERENCES deg_mal(degm_id),
CONSTRAINT fk_bmi_dm_age_rng FOREIGN KEY (agrg_id) REFERENCES age_rng(agrg_id)
);


--Injury
CREATE TABLE IF NOT EXISTS injury(
inj_id INT NOT NULL PRIMARY KEY,
inj VARCHAR(255) NOT NULL,
CONSTRAINT uq_injury_inj UNIQUE (inj)
);


--Temperature
CREATE TABLE IF NOT EXISTS temperature(
temp_id INT NOT NULL PRIMARY KEY,
temp VARCHAR(255) NOT NULL,
CONSTRAINT uq_temperature_temp UNIQUE (temp)
);


--Mobility
CREATE TABLE IF NOT EXISTS mobility(
mob_id INT NOT NULL PRIMARY KEY,
mob VARCHAR(255) NOT NULL,
CONSTRAINT uq_mobility_mob UNIQUE (mob)
);


--Correction factor
CREATE TABLE IF NOT EXISTS cor_factor(
corf_id INT NOT NULL PRIMARY KEY,
corf DOUBLE NOT NULL,
inj_id INT,
temp_id INT,
mob_id INT,
degm_id INT,
CONSTRAINT fk_cor_factor_temperature FOREIGN KEY (temp_id) REFERENCES temperature(temp_id),
CONSTRAINT fk_cor_factor_mobility FOREIGN KEY (mob_id) REFERENCES mobility(mob_id),
CONSTRAINT fk_cor_factor_deg_mal FOREIGN KEY (degm_id) REFERENCES deg_mal(degm_id)
);


--Mixture
CREATE TABLE IF NOT EXISTS mixture(
mix_id INT NOT NULL PRIMARY KEY,
name VARCHAR(255) NOT NULL,
data JSON,
CONSTRAINT uq_mixture_name UNIQUE (name)
);




