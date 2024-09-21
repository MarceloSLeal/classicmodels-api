export const Constants = () => ({
  ...(
    {
      employees: {
        jobTitle: ['President', 'VP Sales', 'VP Marketing', 'Sales Manager (APAC)',
          'Sales Manager (EMEA)', 'Sales Manager (NA)', 'Sales Rep'],
      },
      orders: {
        status: ['SHIPPED', 'RESOLVED', 'CANCELLED', 'ON_HOLD', 'DISPUTED', 'IN_PROCESS'],
      }
    }
  )
})
