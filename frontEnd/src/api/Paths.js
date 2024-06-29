
const prefix = import.meta.env.VITE_URL_PREFIX;

export const Urls = (param) => ({
    ...(
        {
            customers: {
                findAll: `${prefix}customers${param}`,
            }
        }
    )
})
