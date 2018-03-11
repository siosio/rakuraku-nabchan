package example.extension

inline fun <reified T> get(): T {
    return Class.forName(T::class.qualifiedName + "Impl")
            .getConstructor()
            .newInstance() as T
}