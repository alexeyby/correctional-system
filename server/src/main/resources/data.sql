--STATIC DATA



--Age range
--(BMI)
INSERT IGNORE INTO age_rng (agrg_id, age_low, age_high) VALUES (1, 18, 26);
INSERT IGNORE INTO age_rng (agrg_id, age_low, age_high) VALUES (2, 26, 2147483647);
--(SFT - male)
INSERT IGNORE INTO age_rng (agrg_id, age_low, age_high) VALUES (3, 18, 20);
INSERT IGNORE INTO age_rng (agrg_id, age_low, age_high) VALUES (4, 20, 30);
INSERT IGNORE INTO age_rng (agrg_id, age_low, age_high) VALUES (5, 30, 40);
--(SFT - male & female)
INSERT IGNORE INTO age_rng (agrg_id, age_low, age_high) VALUES (6, 40, 50);
INSERT IGNORE INTO age_rng (agrg_id, age_low, age_high) VALUES (7, 50, 2147483647);
--(SFT - female)
INSERT IGNORE INTO age_rng (agrg_id, age_low, age_high) VALUES (8, 18, 40);


--Nutritional status
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (1, 'NORMAL');
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (2, 'OVERWEIGHT');
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (3, 'OBESITY_CLASS_ONE');
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (4, 'OBESITY_CLASS_TWO');
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (5, 'OBESITY_CLASS_THREE');
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (6, 'OBESITY_CLASS_FOUR');
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (7, 'UNDERWEIGHT');
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (8, 'HYPOTROPHY_CLASS_ONE');
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (9, 'HYPOTROPHY_CLASS_TWO');
INSERT IGNORE INTO nut_sts (nsts_id, nsts) VALUES (10, 'HYPOTROPHY_CLASS_THREE');


--Body mass index - nutritional status
--(first age range)
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (1, 19.5, 23.0, 1, 1);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (2, 23.0, 27.5, 2, 1);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (3, 27.5, 30.0, 3, 1);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (4, 30.0, 35.0, 4, 1);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (5, 35.0, 40.0, 5, 1);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (6, 40.0, 1.7976931348623157E+308, 6, 1);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (7, 18.5, 19.5, 7, 1);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (8, 17.0, 18.5, 8, 1);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (9, 15.0, 17.0, 9, 1);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (10, 0.0, 15.0, 10, 1);
--(second age range)
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (11, 20.0, 26.0, 1, 2);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (12, 26.0, 28.0, 2, 2);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (13, 28.0, 31.0, 3, 2);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (14, 31.0, 36.0, 4, 2);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (15, 36.0, 41.0, 5, 2);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (16, 41.0, 1.7976931348623157E+308, 6, 2);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (17, 19.0, 20.0, 7, 2);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (18, 17.5, 19.0, 8, 2);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (19, 15.5, 17.5, 9, 2);
INSERT IGNORE INTO bmi_ns (bmins_id, bmi_low, bmi_high, nsts_id, agrg_id) VALUES (20, 0.0, 15.5, 10, 2);


--Degree of malnutrition
INSERT IGNORE INTO deg_mal (degm_id, degm) VALUES (1, 'NORMAL');
INSERT IGNORE INTO deg_mal (degm_id, degm) VALUES (2, 'MILD');
INSERT IGNORE INTO deg_mal (degm_id, degm) VALUES (3, 'MODERATE');
INSERT IGNORE INTO deg_mal (degm_id, degm) VALUES (4, 'SEVERE');


--Sex
INSERT IGNORE INTO sex (sex_id, sex) VALUES (1, 'MALE');
INSERT IGNORE INTO sex (sex_id, sex) VALUES (2, 'FEMALE');


--Shoulder muscle volume - degree of malnutrition
--(male)
INSERT IGNORE INTO smv_dm (smv_id, smv_low, smv_high, degm_id, sex_id) VALUES (1, 22.8, 1.7976931348623157E+308, 1, 1);
INSERT IGNORE INTO smv_dm (smv_id, smv_low, smv_high, degm_id, sex_id) VALUES (2, 20.2, 22.8, 2, 1);
INSERT IGNORE INTO smv_dm (smv_id, smv_low, smv_high, degm_id, sex_id) VALUES (3, 17.7, 20.2, 3, 1);
INSERT IGNORE INTO smv_dm (smv_id, smv_low, smv_high, degm_id, sex_id) VALUES (4, -1.7976931348623157E+308, 17.7, 4, 1);
--(female)
INSERT IGNORE INTO smv_dm (smv_id, smv_low, smv_high, degm_id, sex_id) VALUES (5, 20.9, 1.7976931348623157E+308, 1, 2);
INSERT IGNORE INTO smv_dm (smv_id, smv_low, smv_high, degm_id, sex_id) VALUES (6, 18.6, 20.9, 2, 2);
INSERT IGNORE INTO smv_dm (smv_id, smv_low, smv_high, degm_id, sex_id) VALUES (7, 16.2, 18.6, 3, 2);
INSERT IGNORE INTO smv_dm (smv_id, smv_low, smv_high, degm_id, sex_id) VALUES (8, -1.7976931348623157E+308, 16.2, 4, 2);


--Skinfold thickness - degree of malnutrition
--(male - first age range)
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (1, 12.0, 1.7976931348623157E+308, 1, 3, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (2, 10.7, 12.0, 2, 3, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (3, 9.4, 10.7, 3, 3, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (4, 0.0, 9.4, 4, 3, 1);
--(male - second age range)
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (5, 13.7, 1.7976931348623157E+308, 1, 4, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (6, 12.2, 13.7, 2, 4, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (7, 10.6, 12.2, 3, 4, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (8, 0.0, 10.6, 4, 4, 1);
--(male - third age range)
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (9, 14.6, 1.7976931348623157E+308, 1, 5, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (10, 13.0, 14.6, 2, 5, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (11, 11.3, 13.0, 3, 5, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (12, 0.0, 11.3, 4, 5, 1);
--(male - fourth age range)
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (13, 14.0, 1.7976931348623157E+308, 1, 6, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (14, 12.5, 14.0, 2, 6, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (15, 10.9, 12.5, 3, 6, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (16, 0.0, 10.9, 4, 6, 1);
--(male - fifth age range)
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (17, 12.4, 1.7976931348623157E+308, 1, 7, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (18, 11.0, 12.4, 2, 7, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (19, 9.7, 11.0, 3, 7, 1);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (20, 0.0, 9.7, 4, 7, 1);
--(female - first age range)
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (21, 10.0, 1.7976931348623157E+308, 1, 8, 2);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (22, 8.9, 10.0, 2, 8, 2);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (23, 7.8, 8.9, 3, 8, 2);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (24, 0.0, 7.8, 4, 8, 2);
--(female - second age range)
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (25, 11.3, 1.7976931348623157E+308, 1, 6, 2);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (26, 10.1, 11.3, 2, 6, 2);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (27, 8.8, 10.1, 3, 6, 2);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (28, 0.0, 8.8, 4, 6, 2);
--(female - third age range)
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (29, 10.5, 1.7976931348623157E+308, 1, 7, 2);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (30, 9.4, 10.5, 2, 7, 2);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (31, 8.2, 9.4, 3, 7, 2);
INSERT IGNORE INTO sft_dm (sft_id, sft_low, sft_high, degm_id, agrg_id, sex_id) VALUES (32, 0.0, 8.2, 4, 7, 2);


--Indicator
INSERT IGNORE INTO indicator (ind_id, ind) VALUES (1, 'ALBUMIN');
INSERT IGNORE INTO indicator (ind_id, ind) VALUES (2, 'TRANSFERRIN');
INSERT IGNORE INTO indicator (ind_id, ind) VALUES (3, 'LYMPHOCYTE');
INSERT IGNORE INTO indicator (ind_id, ind) VALUES (4, 'SKIN_TEST');


--Clinical and laboratory criteria - degree of malnutrition
--(albumin)
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(1, 35.0, 1.7976931348623157E+308, 1, 1);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(2, 30.0, 35.0, 2, 1);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(3, 25.0, 30.0, 3, 1);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(4, 0.0, 25.0, 4, 1);
--(transferrin)
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(5, 2.0, 1.7976931348623157E+308, 1, 2);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(6, 1.8, 2.0, 2, 2);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(7, 1.6, 1.8, 3, 2);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(8, 0.0, 1.6, 4, 2);
--(lymphocyte)
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(9, 1800.0, 1.7976931348623157E+308, 1, 3);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(10, 1500.0, 1800.0, 2, 3);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(11, 900.0, 1500.0, 3, 3);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(12, 0.0, 900.0, 4, 3);
--(skin test)
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(13, 15.0, 1.7976931348623157E+308, 1, 4);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(14, 10.0, 15.0, 2, 4);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(15, 5.0, 10.0, 3, 4);
INSERT IGNORE INTO lab_dm (lab_id, res_low, res_high, degm_id, ind_id) VALUES(16, 0.0, 5.0, 4, 4);


--Body mass index - degree of malnutrition
--(first age range)
INSERT IGNORE INTO bmi_dm (bmidm_id, bmi_low, bmi_high, degm_id, agrg_id) VALUES (1, 18.5, 1.7976931348623157E+308, 1, 1);
INSERT IGNORE INTO bmi_dm (bmidm_id, bmi_low, bmi_high, degm_id, agrg_id) VALUES (2, 17.0, 18.5, 2, 1);
INSERT IGNORE INTO bmi_dm (bmidm_id, bmi_low, bmi_high, degm_id, agrg_id) VALUES (3, 15.0, 17.0, 3, 1);
INSERT IGNORE INTO bmi_dm (bmidm_id, bmi_low, bmi_high, degm_id, agrg_id) VALUES (4, 0.0, 15.0, 4, 1);
--(second age range)
INSERT IGNORE INTO bmi_dm (bmidm_id, bmi_low, bmi_high, degm_id, agrg_id) VALUES (5, 19.0, 1.7976931348623157E+308, 1, 2);
INSERT IGNORE INTO bmi_dm (bmidm_id, bmi_low, bmi_high, degm_id, agrg_id) VALUES (6, 17.5, 19.0, 2, 2);
INSERT IGNORE INTO bmi_dm (bmidm_id, bmi_low, bmi_high, degm_id, agrg_id) VALUES (7, 15.5, 17.5, 3, 2);
INSERT IGNORE INTO bmi_dm (bmidm_id, bmi_low, bmi_high, degm_id, agrg_id) VALUES (8, 0.0, 15.5, 4, 2);


--Injury
INSERT IGNORE INTO injury (inj_id, inj) VALUES (1, 'NO_INJURIES');
INSERT IGNORE INTO injury (inj_id, inj) VALUES (2, 'AFTER_SURGERY');
INSERT IGNORE INTO injury (inj_id, inj) VALUES (3, 'BONE_FRACTURE');
INSERT IGNORE INTO injury (inj_id, inj) VALUES (4, 'SEPSIS');
INSERT IGNORE INTO injury (inj_id, inj) VALUES (5, 'PERITONITIS');
INSERT IGNORE INTO injury (inj_id, inj) VALUES (6, 'POLYTRAUMA_REHABILITATION');
INSERT IGNORE INTO injury (inj_id, inj) VALUES (7, 'POLYTRAUMA_SEPSIS');
INSERT IGNORE INTO injury (inj_id, inj) VALUES (8, 'MODERATE_BURNS');
INSERT IGNORE INTO injury (inj_id, inj) VALUES (9, 'SEVERE_BURNS');
INSERT IGNORE INTO injury (inj_id, inj) VALUES (10, 'VERY_SEVERE_BURNS');


--Temperature
INSERT IGNORE INTO temperature (temp_id, temp) VALUES (1, 'NORMAL');
INSERT IGNORE INTO temperature (temp_id, temp) VALUES (2, 'SLIGHTLY_HIGH');
INSERT IGNORE INTO temperature (temp_id, temp) VALUES (3, 'HIGH');
INSERT IGNORE INTO temperature (temp_id, temp) VALUES (4, 'VERY_HIGH');
INSERT IGNORE INTO temperature (temp_id, temp) VALUES (5, 'EXTREMELY_HIGH');


--Mobility
INSERT IGNORE INTO mobility (mob_id, mob) VALUES (1, 'BEDREST');
INSERT IGNORE INTO mobility (mob_id, mob) VALUES (2, 'AMBULATION');
INSERT IGNORE INTO mobility (mob_id, mob) VALUES (3, 'NO_RESTRICTION');


--Correction factor
INSERT IGNORE INTO cor_factor (corf_id, corf, inj_id, temp_id, mob_id, degm_id) VALUES (1, 1.1, 2, 2, 1, 2);
INSERT IGNORE INTO cor_factor (corf_id, corf, inj_id, temp_id, mob_id, degm_id) VALUES (2, 1.2, 3, 3, 2, 3);
INSERT IGNORE INTO cor_factor (corf_id, corf, inj_id, temp_id, mob_id, degm_id) VALUES (3, 1.3, 4, 4, 3, 4);
INSERT IGNORE INTO cor_factor (corf_id, corf, inj_id, temp_id, mob_id, degm_id) VALUES (4, 1.4, 5, 5, NULL, NULL);
INSERT IGNORE INTO cor_factor (corf_id, corf, inj_id, temp_id, mob_id, degm_id) VALUES (5, 1.5, 6, NULL, NULL, NULL);
INSERT IGNORE INTO cor_factor (corf_id, corf, inj_id, temp_id, mob_id, degm_id) VALUES (6, 1.6, 7, NULL, NULL, NULL);
INSERT IGNORE INTO cor_factor (corf_id, corf, inj_id, temp_id, mob_id, degm_id) VALUES (7, 1.7, 8, NULL, NULL, NULL);
INSERT IGNORE INTO cor_factor (corf_id, corf, inj_id, temp_id, mob_id, degm_id) VALUES (8, 1.8, 9, NULL, NULL, NULL);
INSERT IGNORE INTO cor_factor (corf_id, corf, inj_id, temp_id, mob_id, degm_id) VALUES (9, 1.9, 10, NULL, NULL, NULL);


--Mixture
INSERT IGNORE INTO mixture (mix_id, name, data) VALUES (1, 'ENTERAL',
'{"maxMl": 1200.0, "proteinIn1000Ml": 15.0, "kcaloriesIn1000Ml": 400.0, "ingredients": {
"aminoAcids": {
"glycine": {"amount": 235.0, "percentage": 6.7}, "selenomethionine": {"amount": 0.04, "percentage": 58.0}, 
"LOrnithineLAspartate": {"amount": 460.0, "percentage": 93.0}, "LProline": {"amount": 300.0, "percentage": 7.0}, 
"LSerine": {"amount": 490.0, "percentage": 6.0}, "LAsparticAcid": {"amount": 290.0, "percentage": 2.4}, 
"LGlutamicAcid": {"amount": 720.0, "percentage": 5.3}, "LPhenylalanine": {"amount": 1600.0, "percentage": 54.0}, 
"LTyrosine": {"amount": 1220.0, "percentage": 64.0}, "LLeucine": {"amount": 2500.0, "percentage": 54.0}, 
"LLysineHydrochloride": {"amount": 1900.0, "percentage": 46.0}, "LIsoleucine": {"amount": 900.0, "percentage": 45.0}, 
"LMethionine": {"amount": 800.0, "percentage": 44.0}, "LValine": {"amount": 900.0, "percentage": 36.0}, 
"LTryptophan": {"amount": 600.0, "percentage": 20.0}, "LThreonine": {"amount": 480.0, "percentage": 20.0}, 
"LArginine": {"amount": 1100.0, "percentage": 17.0}, "LHistidine": {"amount": 330.0, "percentage": 16.0}, 
"LAlanine": {"amount": 700.0, "percentage": 11.0}
}, "vitamins": {
"riboflavin": {"amount": 2.0, "percentage": 100.0}, "pyridoxineHydrochloride": {"amount": 2.0, "percentage": 100.0}, 
"nicotinamide": {"amount": 15.0, "percentage": 75.0}
}}}');
INSERT IGNORE INTO mixture (mix_id, name, data) VALUES (2, 'PEPTOMEN', '{}');




