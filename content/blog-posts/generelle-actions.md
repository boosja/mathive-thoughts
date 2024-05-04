:page/title Generelle Actions
:blog-post/tags [:actions :fp]
:blog-post/author {person/id :mathias}
:blog-post/published #time/ldt "2024-05-06T09:00:00"
:blog-post/desc

Bruk få generelle actions til all state oppdatering for optimal genbruk av kode.

:page/body

# Få generelle actions

> "The unavoidable price of reliability is simplicity" – C.A.R. Hoare

```javascript
const associateIn = (obj, path, value) => {
    const result = deepCopy(obj)

    if (path.length === 0) {
        return value
    }

    const associateInPath = (copy, path) => {
        const [firstPath, ...restPath] = path

        if (restPath.length === 0) {
            copy[firstPath] = value
            return copy
        }

        if (!copy[firstPath] || typeof copy[firstPath] !== 'object') {
            copy[firstPath] = {}
        }

        copy[firstPath] = associateInPath(copy[firstPath], restPath)

        return copy
    }

    return associateInPath(result, path)
}
```

```javascript
const updateIn = (obj, path, updateFn, ...args) => {
    const result = deepCopy(obj)

    if (path.length === 0) {
        return updateFn(result, ...args)
    }

    const updateInPath = (copy, path) => {
        const [firstPath, ...restPath] = path

        copy[firstPath] = updateIn(copy[firstPath], restPath, updateFn, ...args)

        return copy
    }

    return updateInPath(result, path)
}
```

```javascript
const deepCopy = (obj) => {
    if (Array.isArray(obj)) {
        return obj.map(item => deepCopy(item))
    }

    if (obj === null) {
        return null
    }

    if (typeof obj === 'object') {
        return Object.keys(obj).reduce(
            (copy, key) => {
                copy[key] = deepCopy(obj[key])
                return copy
            },
            {}
        )
    }

    return obj
}
```
