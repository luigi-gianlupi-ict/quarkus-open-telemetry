package it.ictgroup.utils.elastic;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import org.apache.commons.lang3.tuple.Pair;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.ictgroup.service.AppConstants.*;


public class SearchUtils {

    public enum Operator {
            AND, OR
    }

    public static BoolQuery.Builder query(String prefix, MultivaluedMap<String, String> parameters) {
        return query(prefix, null, parameters);
    }

    public static BoolQuery.Builder query(String prefix, List<String> specialKeys, MultivaluedMap<String, String> parameters) {
        BoolQuery.Builder builder = QueryBuilders.bool();
        query(builder, prefix, specialKeys, parameters, Operator.AND);
        return builder;
    }

    public static BoolQuery.Builder query(BoolQuery.Builder boolQueryBuilder, String prefix, UriInfo ui) {
        return query(boolQueryBuilder, prefix, null, ui.getPathParameters(), Operator.AND);
    }

    public static BoolQuery.Builder query(BoolQuery.Builder boolQueryBuilder, String prefix, List<String> specialKeys, MultivaluedMap<String, String> list) {
        return query(boolQueryBuilder, prefix, specialKeys, list, Operator.AND);
    }

    public static Pair<BoolQuery.Builder,Long> queryPair(BoolQuery.Builder boolQueryBuilder, String prefix, List<String> specialKeys, MultivaluedMap<String, String> list) {
        return queryPair(boolQueryBuilder, prefix, specialKeys, list, Operator.AND);
    }

    /**
     *
     * @param boolQueryBuilder desc
     * @param prefix desc
     * @param specialKeys desc
     * @param list se la key inizia con "!", viene bypassato il controllo sulle specialKeys (es. "obj.!schemaCode" produce una query su "model.schemaCode.raw" invece che su "schemaCode.raw")
     * @param operator desc
     * @return desc
     */
    public static BoolQuery.Builder query(BoolQuery.Builder boolQueryBuilder, String prefix, List<String> specialKeys, MultivaluedMap<String, String> list, Operator operator) {
        for (String key : list.keySet()) {
            if (key.equals("startRow") || key.equals("pageSize") || key.equals("orderBy")) {
                continue;
            }

            String effectivePrefix = getEffectivePrefix(prefix, key, specialKeys);

            if (key.startsWith("obj.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.e(effectivePrefix, getEffectiveKey("obj", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.e(effectivePrefix, getEffectiveKey("obj", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("not.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(s -> s.bool(b -> b.mustNot(SearchUtils.ne(effectivePrefix, getEffectiveKey("not", key), list.getFirst(key)))));
                } else {
                    boolQueryBuilder.mustNot(SearchUtils.ne(effectivePrefix, getEffectiveKey("not", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("like.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.like(effectivePrefix, getEffectiveKey("like", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.like(effectivePrefix, getEffectiveKey("like", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("from.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.gt(effectivePrefix, getEffectiveKey("from", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.gt(effectivePrefix, getEffectiveKey("from", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("to.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.lt(effectivePrefix, getEffectiveKey("to", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.lt(effectivePrefix, getEffectiveKey("to", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("num.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.num(effectivePrefix, getEffectiveKey("num", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.num(effectivePrefix, getEffectiveKey("num", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("num_from.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.numGt(effectivePrefix, getEffectiveKey("num_from", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.numGt(effectivePrefix, getEffectiveKey("num_from", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("num_to.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.numLt(effectivePrefix, getEffectiveKey("num_to", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.numLt(effectivePrefix, getEffectiveKey("num_to", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("bool.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.b(effectivePrefix, getEffectiveKey("bool", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.b(effectivePrefix, getEffectiveKey("bool", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("date.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.d(effectivePrefix, getEffectiveKey("date", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.d(effectivePrefix, getEffectiveKey("date", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("date_to.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.dLt(effectivePrefix, getEffectiveKey("date_to", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.dLt(effectivePrefix, getEffectiveKey("date_to", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("date_from.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.dGt(effectivePrefix, getEffectiveKey("date_from", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.dGt(effectivePrefix, getEffectiveKey("date_from", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("regexp.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.rawRegexp(effectivePrefix, getEffectiveKey("regexp", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.rawRegexp(effectivePrefix, getEffectiveKey("regexp", key), list.getFirst(key)));
                }
            }
            if (key.startsWith("in.")) {
                // FIXME comma separated? semicolon?
                List<String> values = Arrays.stream(list.getFirst(key).split(",")).map(String::trim).collect(Collectors.toList());
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.in(effectivePrefix, getEffectiveKey("in", key), values));
                } else {
                    boolQueryBuilder.must(SearchUtils.in(effectivePrefix, getEffectiveKey("in", key), values));
                }
            }
            if (key.startsWith("nil.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(QueryBuilders.bool(b -> b.mustNot(SearchUtils.exists(effectivePrefix, getEffectiveKey("nil", key)))));
                } else {
                    boolQueryBuilder.mustNot(SearchUtils.exists(effectivePrefix, getEffectiveKey("nil", key)));
                }
            }
            if (key.startsWith("notNil.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.exists(effectivePrefix, getEffectiveKey("notNil", key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.exists(effectivePrefix, getEffectiveKey("notNil", key)));
                }
            }
            if (key.startsWith("notnil.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.exists(effectivePrefix, getEffectiveKey("notnil", key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.exists(effectivePrefix, getEffectiveKey("notnil", key)));
                }
            }
            if (key.startsWith("or.")) {
                String values = list.getFirst(key);
                String keys = key.replace("or.", "");
                String[] splitted = keys.split("\\.");
                int i = 0;
                BoolQuery.Builder should = QueryBuilders.bool();
                while(i < splitted.length) {
                    String first = splitted[i] + "." + splitted[i+1];   // ricostruisco qualcosa tipo "obj.code", "like.description", "nil.dismisisone", ...
                    String val = values.split(";")[i/2];
                    MultivaluedMap<String, String> modified = new MultivaluedHashMap<>();
                    modified.putSingle(first, val);
                    SearchUtils.query(should, prefix, specialKeys, modified, Operator.OR);
                    i += 2;
                }
                boolQueryBuilder.must(should.build()._toQuery());
            }
        }

        return boolQueryBuilder;
    }

    /**
     *
     * @param boolQueryBuilder desc
     * @param prefix desc
     * @param specialKeys desc
     * @param list se la key inizia con "!", viene bypassato il controllo sulle specialKeys (es. "obj.!schemaCode" produce una query su "model.schemaCode.raw" invece che su "schemaCode.raw")
     * @param operator desc
     * @return pair
     */
    public static Pair<BoolQuery.Builder,Long> queryPair(BoolQuery.Builder boolQueryBuilder, String prefix, List<String> specialKeys, MultivaluedMap<String, String> list, Operator operator) {
        long counter = 0L;
        for (String key : list.keySet()) {
            if (key.equals("startRow") || key.equals("pageSize") || key.equals("orderBy")) {
                continue;
            }

            String effectivePrefix = getEffectivePrefix(prefix, key, specialKeys);

            if (key.startsWith("obj.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.e(effectivePrefix, getEffectiveKey("obj", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.e(effectivePrefix, getEffectiveKey("obj", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("not.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(s -> s.bool(b -> b.mustNot(SearchUtils.ne(effectivePrefix, getEffectiveKey("not", key), list.getFirst(key)))));
                } else {
                    boolQueryBuilder.mustNot(SearchUtils.ne(effectivePrefix, getEffectiveKey("not", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("like.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.like(effectivePrefix, getEffectiveKey("like", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.like(effectivePrefix, getEffectiveKey("like", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("from.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.gt(effectivePrefix, getEffectiveKey("from", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.gt(effectivePrefix, getEffectiveKey("from", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("to.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.lt(effectivePrefix, getEffectiveKey("to", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.lt(effectivePrefix, getEffectiveKey("to", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("num.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.num(effectivePrefix, getEffectiveKey("num", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.num(effectivePrefix, getEffectiveKey("num", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("num_from.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.numGt(effectivePrefix, getEffectiveKey("num_from", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.numGt(effectivePrefix, getEffectiveKey("num_from", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("num_to.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.numLt(effectivePrefix, getEffectiveKey("num_to", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.numLt(effectivePrefix, getEffectiveKey("num_to", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("bool.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.b(effectivePrefix, getEffectiveKey("bool", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.b(effectivePrefix, getEffectiveKey("bool", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("date.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.d(effectivePrefix, getEffectiveKey("date", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.d(effectivePrefix, getEffectiveKey("date", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("date_to.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.dLt(effectivePrefix, getEffectiveKey("date_to", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.dLt(effectivePrefix, getEffectiveKey("date_to", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("date_from.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.dGt(effectivePrefix, getEffectiveKey("date_from", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.dGt(effectivePrefix, getEffectiveKey("date_from", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("regexp.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.rawRegexp(effectivePrefix, getEffectiveKey("regexp", key), list.getFirst(key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.rawRegexp(effectivePrefix, getEffectiveKey("regexp", key), list.getFirst(key)));
                }
                counter++;
            }
            if (key.startsWith("in.")) {
                // FIXME comma separated? semicolon?
                List<String> values = Arrays.stream(list.getFirst(key).split(",")).map(String::trim).collect(Collectors.toList());
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.in(effectivePrefix, getEffectiveKey("in", key), values));
                } else {
                    boolQueryBuilder.must(SearchUtils.in(effectivePrefix, getEffectiveKey("in", key), values));
                }
                counter++;
            }
            if (key.startsWith("nil.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(QueryBuilders.bool(b -> b.mustNot(SearchUtils.exists(effectivePrefix, getEffectiveKey("nil", key)))));
                } else {
                    boolQueryBuilder.mustNot(SearchUtils.exists(effectivePrefix, getEffectiveKey("nil", key)));
                }
                counter++;
            }
            if (key.startsWith("notNil.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.exists(effectivePrefix, getEffectiveKey("notNil", key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.exists(effectivePrefix, getEffectiveKey("notNil", key)));
                }
                counter++;
            }
            if (key.startsWith("notnil.")) {
                if(Operator.OR == operator) {
                    boolQueryBuilder.should(SearchUtils.exists(effectivePrefix, getEffectiveKey("notnil", key)));
                } else {
                    boolQueryBuilder.must(SearchUtils.exists(effectivePrefix, getEffectiveKey("notnil", key)));
                }
                counter++;
            }
            if (key.startsWith("or.")) {
                String values = list.getFirst(key);
                String keys = key.replace("or.", "");
                String[] splitted = keys.split("\\.");
                int i = 0;
                BoolQuery.Builder should = QueryBuilders.bool();
                while(i < splitted.length) {
                    String first = splitted[i] + "." + splitted[i+1];   // ricostruisco qualcosa tipo "obj.code", "like.description", "nil.dismisisone", ...
                    String val = values.split(";")[i/2];
                    MultivaluedMap<String, String> modified = new MultivaluedHashMap<>();
                    modified.putSingle(first, val);
                    SearchUtils.query(should, prefix, specialKeys, modified, Operator.OR);
                    i += 2;
                }
                boolQueryBuilder.must(should.build()._toQuery());
                counter++;
            }
        }

        return Pair.of(boolQueryBuilder,counter);
    }

    public static String getEffectivePrefix(String prefix, String key, List<String> specialKeys) {
        String[] splitted = key.split("\\.");
        if(splitted.length > 1) {
            // trovo la key effettiva (senza il prefisso obj., not., ecc)
            if (specialKeys != null && specialKeys.contains(splitted[1])) {
                return null;
            }
        }
        return prefix;
    }

    public static String getEffectiveKey(String op, String key) {
        String postfix = op.trim().endsWith(".") ? "" : ".";
        String k = key.replace(op.trim()+postfix, "");
        if(k.startsWith("!")) {
            k = k.replaceFirst("!", "");
        }
        return k;
    }

    public static void orderBy(SearchRequest.Builder searchRequestBuilder, String prefix, String orderBy) {
        SearchUtils.orderBy(prefix, orderBy).forEach(searchRequestBuilder::sort);
    }

    public static Query rawRegexp(String prefix, String key, String value) {
        return QueryBuilders.regexp(r -> r.field(getFullKey(prefix, key, true)).value(value));
    }

    public static Query like(String prefix, String key, String value) {
        return QueryBuilders.regexp(r -> r.field(getFullKey(prefix, key, true)).value(".*" + value + ".*"));
    }

    public static Query e(String prefix, String key, String value) {
        return QueryBuilders.match().field(getFullKey(prefix, key, true)).query(value).build()._toQuery();
    }

    public static Query ne(String prefix, String key, String value) {
        return e(prefix, key, value);
    }

    public static Query lt(String prefix, String key, String value) {
        return QueryBuilders.range(r -> r.field(getFullKey(prefix, key, true)).lt(JsonData.of(value)));
    }

    @SuppressWarnings("unused")
    public static Query lte(String prefix, String key, String value) {
        return QueryBuilders.range(r -> r.field(getFullKey(prefix, key, true)).lte(JsonData.of(value)));
    }

    public static Query gt(String prefix, String key, String value) {
        return QueryBuilders.range(r -> r.field(getFullKey(prefix, key, true)).gt(JsonData.of(value)));
    }

    @SuppressWarnings("unused")
    public static Query gte(String prefix, String key, String value) {
        return QueryBuilders.range(r -> r.field(getFullKey(prefix, key, true)).gte(JsonData.of(value)));
    }

    public static Query b(String prefix, String key, String value) {
        return QueryBuilders.match(builder -> builder.field(getFullKey(prefix, key, false)).query(value));
    }

    public static Query d(String prefix, String key, String value) {
        if(value.startsWith("$") && value.endsWith("$")) {
            return script(getFullKey(prefix, key, false), getFullKey(prefix, value.replaceAll("\\$", ""), false), "==", true);
        }
        return QueryBuilders.match(m -> m.query(value).field(getFullKey(prefix, key, false)));
    }

    public static Query dLt(String prefix, String key, String value) {

        if(value.startsWith("$") && value.endsWith("$")) {
            return script(getFullKey(prefix, key, false), getFullKey(prefix, value.replaceAll("\\$", ""), false), "<", true);
        }
        return QueryBuilders.range(r -> r.field(getFullKey(prefix, key, false)).lt(JsonData.of(value)));
    }

    public static Query dGt(String prefix, String key, String value) {
        if(value.startsWith("$") && value.endsWith("$")) {
            return script(getFullKey(prefix, key, false), getFullKey(prefix, value.replaceAll("\\$", ""), false), ">", true);
        }
        return QueryBuilders.range(r -> r.field(getFullKey(prefix, key, false)).gt(JsonData.of(value)));
    }

    public static Query num(String prefix, String key, String value) {
        if(value.startsWith("$") && value.endsWith("$")) {
            return script(getFullKey(prefix, key, false), getFullKey(prefix, value.replaceAll("\\$", ""), false), "==", false);
        }
        return QueryBuilders.match(m -> m.query(value).field(getFullKey(prefix, key, false)));
    }

    public static Query numLt(String prefix, String key, String value) {
        if(value.startsWith("$") && value.endsWith("$")) {
            return script(getFullKey(prefix, key, false), getFullKey(prefix, value.replaceAll("\\$", ""), false), "<", false);
        }
        return QueryBuilders.range(r -> r.field(getFullKey(prefix, key, false)).lt(JsonData.of(value)));
    }

    public static Query numGt(String prefix, String key, String value) {
        if(value.startsWith("$") && value.endsWith("$")) {
            return script(getFullKey(prefix, key, false), getFullKey(prefix, value.replaceAll("\\$", ""), false), ">", false);
        }
        return QueryBuilders.range().field(getFullKey(prefix, key, false)).gt(JsonData.of(value)).build()._toQuery();
    }

    public static Query in(String prefix, String key, List<String> values) {
        return QueryBuilders.terms(t -> t.field(getFullKey(prefix, key, true)).terms(ts -> ts.value(values.stream().map(FieldValue::of).collect(Collectors.toList()))));
    }

    public static Query exists(String prefix, String key) {
        return QueryBuilders.exists(e -> e.field(getFullKey(prefix, key, false)));
    }

    public static Query script(String left, String right, String op, boolean areDates) {
        if(areDates) {
            return QueryBuilders.script().script(s -> s.inline(i -> i.source(String.format("doc['%s'].date %s doc['%s'].date", left, op, right)))).build()._toQuery();
        } else {
            return QueryBuilders.script().script(s -> s.inline(i -> i.source(String.format("doc['%s'].value %s doc['%s'].value", left, op, right)))).build()._toQuery();
        }
    }

    public static Stream<SortOptions> orderBy(String prefix, String value) {
        return orderBy(prefix, value, new ArrayList<>());
    }

    public static Stream<SortOptions> orderBy(String prefix, String value, List<String> additionalBlacklist) {
        List<String> blk = Arrays.asList(DATAIN, DATAOUT, CREATION_DATE, // generic
                DATE_INSERTION, DATE_REQUESTED, DATE_HANDLING, DATE_CLOSE); // ODL
        List<String> blacklist = new ArrayList<>();
        blacklist.addAll(blk);
        blacklist.addAll(additionalBlacklist);

        return Optional.ofNullable(value)
                .map(v -> v.split(",")).stream().flatMap(Arrays::stream)
                .map(String::trim)
                .map(s -> s.split(" "))
                .map(keyValue -> {
                    final SortOrder sortOrder = Optional.of(keyValue)
                            .filter(a -> a.length == 2)
                            .map(a -> a[1])
                            .map(String::trim)
                            .map(String::toUpperCase)
                            .map(name -> {
                                try {
                                    return SortOrder.valueOf(name);
                                } catch (Exception e) {
                                    return SortOrder.Asc;
                                }
                            }).orElse(SortOrder.Asc);

                    boolean raw = !blacklist.contains(keyValue[0]);
                    return SortOptions.of(o -> o.field(f -> f.field(getFullKey(prefix, keyValue[0], raw))
                            .order(sortOrder)
                            .missing(LAST)));

                 /*   return SortBuilders.fieldSort(getFullKey(prefix, keyValue[0], raw))
                            .order(sortOrder)
                            .missing("_last");*/
                });
    }


    private static String getFullKey(String prefix, String key, boolean raw) {
        return getFullKey(prefix, key, raw, Arrays.asList(SCHEMA_CODE, UUID, VERSION, USER));
    }

    private static String getFullKey(String prefix, String key, boolean raw, List<String> reservedKeys) {
        String fullKey = key.trim();
        if (prefix != null && !prefix.trim().isEmpty() && !reservedKeys.contains(key.trim())) {
            fullKey = prefix + "." + key;
        }
        if (raw) {
            fullKey += "." + RAW;
        }
        return fullKey;
    }

    public static Query matchOrMissing(String key, String postfix, Object value) {
        return QueryBuilders.bool()
                .should(s -> s.match(m -> m.field(key + postfix).query(builder -> castValue(builder, value))))
                .should(s -> s.bool(b -> b.mustNot(m-> m.exists(e -> e.field(key))))).build()._toQuery();
    }

    private static ObjectBuilder<FieldValue> castValue(FieldValue.Builder builder, Object value) {
        if (value instanceof Long) {
            return builder.longValue((long) value);
        } else if (value instanceof Integer) {
            return builder.longValue(Long.valueOf((Integer) value));
        } else if (value instanceof Double) {
            return builder.doubleValue((double) value);
        } else if (value instanceof Boolean) {
            return builder.booleanValue((boolean) value);
        } else if (value instanceof String) {
            return builder.stringValue((String) value);
        }
        return builder.nullValue();
    }

    @SuppressWarnings("unused")
    public static Query lteOrMissing(String key, Object value) {
        return QueryBuilders.bool(b -> b.should(s -> s.range(r -> r.field(key).lte(JsonData.of(value))))
                        .should(s -> s.bool(o -> o.mustNot(m -> m.exists(e -> e.field(key))))));
    }

    public static Query gteOrMissing(String key, Object value) {
        return QueryBuilders.bool(b-> b.should(s -> s.range(r -> r.field(key).gte(JsonData.of(value))))
                .should(s -> s.bool(o -> o.mustNot(m -> m.exists(e -> e.field(key))))));

    }

    public static Query buildCommissionQuery(String commission) {
        BoolQuery.Builder commissionQueryBuilder = QueryBuilders.bool()
                .should(s -> s.bool(b -> b.mustNot(m -> m.exists(e -> e.field(COMMISSION)))));

        Optional.ofNullable(commission)
                .ifPresent(c -> commissionQueryBuilder
                        .should(s -> s.match(m -> m.field(COMMISSION + RAW_POSTFIX).query(commission))));
        return commissionQueryBuilder.build()._toQuery();
    }

    @SuppressWarnings("unused")
    public static String[] getFetchFields(String csvFields) {
        return Optional.ofNullable(csvFields)
                .map(s -> s.split(","))
                .orElse(new String[]{"*"});
    }
}
