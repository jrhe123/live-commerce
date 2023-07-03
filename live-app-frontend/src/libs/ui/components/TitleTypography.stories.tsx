import { ComponentStory, ComponentMeta } from '@storybook/react'
import React from 'react'

// component
import TitleTypography from 'libs/ui/components/TitleTypography'

export default {
  title: 'UI/components/TitleTypography',
  component: TitleTypography,
} as ComponentMeta<typeof TitleTypography>

const Template: ComponentStory<typeof TitleTypography> = args => <TitleTypography {...args} />

/**
 * option 1
 */
export const EmptyTitle = Template.bind({})
EmptyTitle.args = {
  title: '',
}

/**
 * option 2
 */
export const WithTitle = Template.bind({})
WithTitle.args = {
  title: 'Hello World',
}
