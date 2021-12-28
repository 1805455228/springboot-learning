import is, {hasProperty} from '@form-create/utils/lib/type';
import {parseFn} from '@form-create/utils/lib/json';

export function makeOptionsRule(to) {
    return {
        type: 'radio',
        title: '选项数据',
        field: '_optionType',
        value: 0,
        options: [
            {'label': '静态数据', 'value': 0},
            {'label': '接口数据', 'value': 1},
        ],
        props: {
            type: 'button'
        },
        control: [
            {
                value: 0,
                rule: [
                    {
                        type: 'Struct',
                        field: 'formCreate' + upper(to).replace('.', '>'),
                        props: {defaultValue: []}
                    },
                ],
            },
            {
                value: 1,
                rule: [
                    {
                        type: 'Fetch',
                        field: 'formCreateEffect>fetch',
                        props: {
                            to
                        }
                    }
                ]
            }
        ]

    };
}

export function upper(str) {
    return str.replace(str[0], str[0].toLocaleUpperCase());
}


export const toJSON = function (val) {
    const type = /object ([a-zA-Z]*)/.exec(Object.prototype.toString.call(val));
    if (type && _toJSON[type[1].toLowerCase()]) {
        return _toJSON[type[1].toLowerCase()](val);
    } else {
        return val;
    }
};

const _toJSON = {
    object: function (val) {
        var json = [];
        for (var i in val) {
            if (!hasProperty(val, i)) continue;
            json.push(
                toJSON(i) + ': ' +
                ((val[i] != null) ? toJSON(val[i]) : 'null')
            );
        }
        return '{\n ' + json.join(',\n ') + '\n}';
    },
    array: function (val) {
        for (var i = 0, json = []; i < val.length; i++)
            json[i] = (val[i] != null) ? toJSON(val[i]) : 'null';
        return '[' + json.join(', ') + ']';
    },
    string: function (val) {
        var tmp = val.split('');
        for (var i = 0; i < tmp.length; i++) {
            var c = tmp[i];
            (c >= ' ') ?
                (c === '\\') ? (tmp[i] = '\\\\') :
                    (c === '"') ? (tmp[i] = '\\"') : 0 :
                (tmp[i] =
                        (c === '\n') ? '\\n' :
                            (c === '\r') ? '\\r' :
                                (c === '\t') ? '\\t' :
                                    (c === '\b') ? '\\b' :
                                        (c === '\f') ? '\\f' :
                                            (c = c.charCodeAt(), ('\\u00' + ((c > 15) ? 1 : 0) + (c % 16)))
                );
        }
        return '"' + tmp.join('') + '"';
    }
};

export const deepParseFn = function (target) {
    for (let key in target) {
        if (Object.prototype.hasOwnProperty.call(target, key)) {
            let data = target[key];
            if (Array.isArray(data) || is.Object(data)) {
                deepParseFn(data);
            }
            if (is.String(data)) {
                target[key] = parseFn(data);
            }
        }
    }
    return target;
};
