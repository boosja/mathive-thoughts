/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.clj"],
  // darkMode: 'selector',
  theme: {
    backgroundSize: {
      sm: '50px',
    },
    container: {
      center: true,
    },
    extend: {
      typography: theme => ({
        DEFAULT: {
          css: {
            a: {
              color: theme('colors.blue.600')
            },
            'a:hover': {
              color: theme('colors.blue.500')
            },
            code: {
              padding: '4px 8px',
              // border: '1px solid grey',
              borderRadius: '4px',
              backgroundColor: theme('colors.black'),
              color: theme('colors.zinc.400'),
            },
            'code::before': { content: '""' },
            'code::after': { content: '""' },
            blockquote: {
              quotes: "none",
            },
          }
        },
        invert: {
          css: {
            color: theme('colors.slate.400')
          }
        }
      }),
      backgroundImage: {
        'texture-light': 'url("images/bg_light.svg")',
        'texture-dark': 'url("images/bg_dark.svg")'
      },
      fontFamily: {
        swanky: '"Fontdiner Swanky", serif',
      }
    },
  },
  plugins: [
    require('@tailwindcss/typography')
  ],
}

