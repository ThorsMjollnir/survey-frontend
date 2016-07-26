SELECT * FROM (
	SELECT DISTINCT ON (code) categories.code, COALESCE(t1.local_description, t2.local_description) AS local_description
	FROM categories
	LEFT JOIN categories_local as t1
		ON categories.code = t1.category_code AND t1.locale_id = {locale_id}
	LEFT JOIN categories_local as t2
		ON categories.code = t2.category_code AND t2.locale_id IN (SELECT prototype_locale_id FROM locales WHERE id = {locale_id})
	LEFT JOIN categories_categories
		ON categories.code = categories_categories.subcategory_code
	LEFT JOIN categories as parent_categories
		ON parent_categories.code = categories_categories.category_code
	WHERE
		(COALESCE(t1.local_description, t2.local_description) IS NOT NULL)
		AND (categories_categories.category_code IS NULL OR parent_categories.is_hidden)
		AND (NOT categories.is_hidden)
	ORDER BY code
) AS t1
ORDER BY local_description;

