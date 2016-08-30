WITH v AS(
  SELECT (SELECT code FROM foods WHERE code={food_code}) AS food_code,
         (SELECT id FROM locales WHERE id={locale_id}) AS locale_id
)
SELECT 
  v.food_code, v.locale_id, psm.id, psm.method, psm.description, psm.image_url, psm.use_for_recipes,
       par.id as param_id, par.name as param_name, par.value as param_value
       FROM v LEFT JOIN foods_portion_size_methods AS psm ON psm.food_code=v.food_code AND psm.locale_id=v.locale_id
              LEFT JOIN foods_portion_size_method_params AS par ON psm.id = par.portion_size_method_id
ORDER BY param_id